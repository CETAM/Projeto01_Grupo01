package br.com.cetam.biblioteca.service;

import br.com.cetam.biblioteca.entity.Livro;
import br.com.cetam.biblioteca.repository.LivroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LivroService {
    private final LivroRepository repository;
    public LivroService(LivroRepository repository) { this.repository = repository; }

    public Page<Livro> listar(int pagina) { return repository.findAll(PageRequest.of(pagina, 8)); }
    public java.util.List<Livro> listarTodos() { return repository.findAll(); }
    public Livro buscar(Long id) { return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Livro não encontrado")); }

    @Transactional
    public Livro salvar(Livro livro) {
        if (livro.getId() == null) {
            livro.setQuantidadeDisponivel(livro.getQuantidadeTotal());
        } else {
            Livro atual = buscar(livro.getId());
            int emprestados = atual.getQuantidadeTotal() - atual.getQuantidadeDisponivel();
            int novaDisponibilidade = Math.max(0, livro.getQuantidadeTotal() - emprestados);
            livro.setQuantidadeDisponivel(novaDisponibilidade);
        }
        return repository.save(livro);
    }

    public void excluir(Long id) { repository.deleteById(id); }
    public long contar() { return repository.count(); }
    public long contarDisponiveis() { return repository.countByQuantidadeDisponivelGreaterThan(0); }
}
