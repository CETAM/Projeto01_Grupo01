package br.com.cetam.biblioteca.repository;
import br.com.cetam.biblioteca.entity.Multa;
import br.com.cetam.biblioteca.entity.Multa.StatusMulta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface MultaRepository extends JpaRepository<Multa, Long> {
    long countByStatus(StatusMulta status);
    Optional<Multa> findByEmprestimoId(Long emprestimoId);
}
