package tn0esprit.gestionbibliothequespringmicroservice.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn0esprit.gestionbibliothequespringmicroservice.Entity.Livre;
import tn0esprit.gestionbibliothequespringmicroservice.Repositories.LivreRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class LivreService implements ILivreService {

    @Autowired
    private LivreRepository livreRepository;

    @Override
    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }

    @Override
    public Optional<Livre> getLivreById(Long id) {
        return livreRepository.findById(id);
    }

    @Override
    public Livre saveLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    @Override
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
            return livreRepository.save(livre);
        }).orElse(null);
    }

    @Override
    public void deleteLivre(Long id) {
        livreRepository.deleteById(id);
    }





}
