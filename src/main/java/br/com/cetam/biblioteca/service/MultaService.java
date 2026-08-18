package br.com.cetam.biblioteca.service;

import br.com.cetam.biblioteca.entity.Multa;
import br.com.cetam.biblioteca.entity.Multa.StatusMulta;
import br.com.cetam.biblioteca.repository.MultaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class MultaService {
    private final MultaRepository repository;
    public MultaService(MultaRepository repository) { this.repository = repository; }

    public Page<Multa> listar(int pagina) { return repository.findAll(PageRequest.of(pagina, 8)); }
    public Multa buscar(Long id) { return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Multa não encontrada")); }
    public Multa salvar(Multa multa) { return repository.save(multa); }
    public void excluir(Long id) { repository.deleteById(id); }
    public long contarPendentes() { return repository.countByStatus(StatusMulta.PENDENTE); }

    @Transactional
    public void pagar(Long id) {
        Multa multa = buscar(id);
        multa.setStatus(StatusMulta.PAGA);
        multa.setDataPagamento(LocalDate.now());
        repository.save(multa);
    }
}
