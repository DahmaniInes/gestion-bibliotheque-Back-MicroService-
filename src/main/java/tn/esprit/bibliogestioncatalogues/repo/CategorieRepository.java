package tn.esprit.bibliogestioncatalogues.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.bibliogestioncatalogues.entities.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
