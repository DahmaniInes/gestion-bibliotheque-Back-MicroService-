package tn.esprit.bibliogestioncatalogues.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.bibliogestioncatalogues.entities.Livre;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LivreRepository extends JpaRepository<Livre, Long>, JpaSpecificationExecutor<Livre> {
}