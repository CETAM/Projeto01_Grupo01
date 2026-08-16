package br.com.cetam.biblioteca.service;

import br.com.cetam.biblioteca.entity.Autor;
import br.com.cetam.biblioteca.repository.AutorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AutorService {
    private final AutorRepository repository;
    public AutorService(AutorRepository repository) { this.repository = repository; }
    public Page<Autor> listar(int pagina) { return repository.findAll(PageRequest.of(pagina, 8)); }
    public java.util.List<Autor> listarTodos() { return repository.findAll(); }
    public Autor buscar(Long id) { return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Autor não encontrado")); }
    public Autor salvar(Autor autor) { return repository.save(autor); }
    public void excluir(Long id) { repository.deleteById(id); }
    public long contar() { return repository.count(); }
}
