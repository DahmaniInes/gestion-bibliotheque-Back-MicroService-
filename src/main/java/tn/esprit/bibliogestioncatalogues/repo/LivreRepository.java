package tn.esprit.bibliogestioncatalogues.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.bibliogestioncatalogues.entities.Livre;

public interface LivreRepository extends JpaRepository<Livre, Long> {
}
