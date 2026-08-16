package br.com.cetam.biblioteca.repository;
import br.com.cetam.biblioteca.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LivroRepository extends JpaRepository<Livro, Long> {
    long countByQuantidadeDisponivelGreaterThan(Integer quantidade);
}
