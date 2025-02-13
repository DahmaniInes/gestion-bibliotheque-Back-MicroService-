package tn.esprit.bibliogestioncatalogues.services;

import tn.esprit.bibliogestioncatalogues.entities.Livre;

import java.util.List;
import java.util.Optional;

public interface ILivreService {
    List<Livre> getAllLivres();
    Optional<Livre> getLivreById(Long id);
    Livre saveLivre(Livre livre);
    Livre updateLivre(Long id, Livre livre);
    void deleteLivre(Long id);
}
