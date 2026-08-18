package br.com.cetam.biblioteca.service;

import br.com.cetam.biblioteca.entity.Emprestimo;
import br.com.cetam.biblioteca.entity.Emprestimo.StatusEmprestimo;
import br.com.cetam.biblioteca.entity.Livro;
import br.com.cetam.biblioteca.entity.Multa;
import br.com.cetam.biblioteca.entity.Multa.StatusMulta;
import br.com.cetam.biblioteca.repository.EmprestimoRepository;
import br.com.cetam.biblioteca.repository.LivroRepository;
import br.com.cetam.biblioteca.repository.MultaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class EmprestimoService {
    private final EmprestimoRepository repository;
    private final LivroRepository livroRepository;
    private final MultaRepository multaRepository;

    public EmprestimoService(EmprestimoRepository repository, LivroRepository livroRepository, MultaRepository multaRepository) {
        this.repository = repository;
        this.livroRepository = livroRepository;
        this.multaRepository = multaRepository;
    }

    public Page<Emprestimo> listar(int pagina) {
        atualizarAtrasados();
        return repository.findAll(PageRequest.of(pagina, 8));
    }

    public Emprestimo buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado"));
    }

    @Transactional
    public Emprestimo salvar(Emprestimo emprestimo) {
        if (emprestimo.getId() == null) {
            Livro livro = livroRepository.findById(emprestimo.getLivro().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
            if (livro.getQuantidadeDisponivel() <= 0) {
                throw new IllegalStateException("Este livro não possui exemplares disponíveis");
            }
            livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);
            livroRepository.save(livro);
            emprestimo.setDataEmprestimo(LocalDate.now());
            emprestimo.setStatus(StatusEmprestimo.EMPRESTADO);
            return repository.save(emprestimo);
        }

        Emprestimo atual = buscar(emprestimo.getId());
        if (atual.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new IllegalStateException("Empréstimo já devolvido não pode ser alterado");
        }

        Long livroAntigoId = atual.getLivro().getId();
        Long livroNovoId = emprestimo.getLivro().getId();
        if (!livroAntigoId.equals(livroNovoId)) {
            Livro antigo = atual.getLivro();
            antigo.setQuantidadeDisponivel(Math.min(antigo.getQuantidadeTotal(), antigo.getQuantidadeDisponivel() + 1));
            livroRepository.save(antigo);

            Livro novo = livroRepository.findById(livroNovoId)
                    .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
            if (novo.getQuantidadeDisponivel() <= 0) {
                throw new IllegalStateException("O novo livro selecionado não possui exemplares disponíveis");
            }
            novo.setQuantidadeDisponivel(novo.getQuantidadeDisponivel() - 1);
            livroRepository.save(novo);
            atual.setLivro(novo);
        }

        atual.setAluno(emprestimo.getAluno());
        atual.setDataPrevistaDevolucao(emprestimo.getDataPrevistaDevolucao());
        return repository.save(atual);
    }

    @Transactional
    public void devolver(Long id) {
        Emprestimo emprestimo = buscar(id);
        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new IllegalStateException("Este empréstimo já foi devolvido");
        }

        LocalDate hoje = LocalDate.now();
        emprestimo.setDataDevolucao(hoje);
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);

        Livro livro = emprestimo.getLivro();
        livro.setQuantidadeDisponivel(Math.min(livro.getQuantidadeTotal(), livro.getQuantidadeDisponivel() + 1));
        livroRepository.save(livro);

        if (hoje.isAfter(emprestimo.getDataPrevistaDevolucao())) {
            long dias = ChronoUnit.DAYS.between(emprestimo.getDataPrevistaDevolucao(), hoje);
            if (multaRepository.findByEmprestimoId(id).isEmpty()) {
                Multa multa = new Multa();
                multa.setEmprestimo(emprestimo);
                multa.setDiasAtraso((int) dias);
                multa.setValor(BigDecimal.valueOf(dias).multiply(new BigDecimal("2.00")));
                multa.setStatus(StatusMulta.PENDENTE);
                multa.setDataGeracao(hoje);
                multaRepository.save(multa);
            }
        }
        repository.save(emprestimo);
    }

    @Transactional
    public void excluir(Long id) {
        Emprestimo e = buscar(id);
        if (e.getStatus() != StatusEmprestimo.DEVOLVIDO) {
            Livro livro = e.getLivro();
            livro.setQuantidadeDisponivel(Math.min(livro.getQuantidadeTotal(), livro.getQuantidadeDisponivel() + 1));
            livroRepository.save(livro);
        }
        multaRepository.findByEmprestimoId(id).ifPresent(multaRepository::delete);
        repository.delete(e);
    }

    @Transactional
    public void atualizarAtrasados() {
        LocalDate hoje = LocalDate.now();
        for (Emprestimo e : repository.findAll()) {
            if (e.getStatus() == StatusEmprestimo.EMPRESTADO &&
                    e.getDataPrevistaDevolucao() != null &&
                    hoje.isAfter(e.getDataPrevistaDevolucao())) {
                e.setStatus(StatusEmprestimo.ATRASADO);
                repository.save(e);
            }
        }
    }

    public long contarAtivos() {
        atualizarAtrasados();
        return repository.countByStatus(StatusEmprestimo.EMPRESTADO)
                + repository.countByStatus(StatusEmprestimo.ATRASADO);
    }

    public long contarAtrasados() {
        atualizarAtrasados();
        return repository.countByStatus(StatusEmprestimo.ATRASADO);
    }

    public long contarTotal() { return repository.count(); }
}
