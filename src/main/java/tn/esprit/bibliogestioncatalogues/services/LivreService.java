package tn.esprit.bibliogestioncatalogues.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.bibliogestioncatalogues.entities.Livre;
import tn.esprit.bibliogestioncatalogues.repo.LivreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LivreService implements ILivreService {
    @Autowired
    private LivreRepository livreRepository;

    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    public Optional<Livre> getLivreById(Long id) {
        return livreRepository.findById(id);
    }

    public Livre saveLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    public Livre updateLivre(Long id, Livre updatedLivre) {
        return livreRepository.findById(id).map(livre -> {
            livre.setTitre(updatedLivre.getTitre());
            livre.setIsbn(updatedLivre.getIsbn());
            livre.setAnneePublication(updatedLivre.getAnneePublication());
            livre.setEditeur(updatedLivre.getEditeur());
            livre.setNombrePages(updatedLivre.getNombrePages());
            livre.setResume(updatedLivre.getResume());
            livre.setPrix(updatedLivre.getPrix());
            livre.setStockDisponible(updatedLivre.getStockDisponible());
            livre.setImageCouverture(updatedLivre.getImageCouverture());
            livre.setCategorie(updatedLivre.getCategorie());
            livre.setAuteurs(updatedLivre.getAuteurs());
            return livreRepository.save(livre);
        }).orElse(null);
    }

    public void deleteLivre(Long id) {
        livreRepository.deleteById(id);
    }
}
