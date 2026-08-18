package br.com.cetam.biblioteca.service;

import br.com.cetam.biblioteca.entity.Aluno;
import br.com.cetam.biblioteca.repository.AlunoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {
    private final AlunoRepository repository;
    public AlunoService(AlunoRepository repository) { this.repository = repository; }
    public Page<Aluno> listar(int pagina) { return repository.findAll(PageRequest.of(pagina, 8)); }
    public java.util.List<Aluno> listarTodos() { return repository.findAll(); }
    public Aluno buscar(Long id) { return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado")); }
    public Aluno salvar(Aluno aluno) { return repository.save(aluno); }
    public void excluir(Long id) { repository.deleteById(id); }
    public long contar() { return repository.count(); }
}
