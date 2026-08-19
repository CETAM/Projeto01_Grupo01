package br.com.cetam.biblioteca.repository;
import br.com.cetam.biblioteca.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AlunoRepository extends JpaRepository<Aluno, Long> { }
