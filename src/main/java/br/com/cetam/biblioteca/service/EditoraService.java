package br.com.cetam.biblioteca.service;

import br.com.cetam.biblioteca.entity.Editora;
import br.com.cetam.biblioteca.repository.EditoraRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EditoraService {
    private final EditoraRepository repository;
    public EditoraService(EditoraRepository repository) { this.repository = repository; }
    public Page<Editora> listar(int pagina) { return repository.findAll(PageRequest.of(pagina, 8)); }
    public java.util.List<Editora> listarTodos() { return repository.findAll(); }
    public Editora buscar(Long id) { return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Editora não encontrada")); }
    public Editora salvar(Editora editora) { return repository.save(editora); }
    public void excluir(Long id) { repository.deleteById(id); }
    public long contar() { return repository.count(); }
}
