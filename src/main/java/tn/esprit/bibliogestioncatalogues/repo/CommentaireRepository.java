package tn.esprit.bibliogestioncatalogues.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.bibliogestioncatalogues.entities.Commentaire;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    List<Commentaire> findByLivreId(Long livreId);
}
