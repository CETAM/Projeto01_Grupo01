package br.com.cetam.biblioteca.repository;
import br.com.cetam.biblioteca.entity.Emprestimo;
import br.com.cetam.biblioteca.entity.Emprestimo.StatusEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    long countByStatus(StatusEmprestimo status);
}
