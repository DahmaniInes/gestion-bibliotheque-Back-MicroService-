package tn.esprit.bibliogestioncatalogues.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.bibliogestioncatalogues.entities.Auteur;

public interface AuteurRepository extends JpaRepository<Auteur, Long> {
}
