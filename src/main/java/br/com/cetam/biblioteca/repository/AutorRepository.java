package br.com.cetam.biblioteca.repository;
import br.com.cetam.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AutorRepository extends JpaRepository<Autor, Long> { }
