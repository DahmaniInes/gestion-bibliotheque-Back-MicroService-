package tn.esprit.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecommerce.entities.lignepanier;
import tn.esprit.ecommerce.repositories.lignepanierrepository;

import java.util.List;
import java.util.Optional;

@Service
public class lignepanierservice {
    @Autowired
    private lignepanierrepository lignePanierRepository;

    public List<lignepanier> getAllLignePaniers() {
        return lignePanierRepository.findAll();
    }

    public Optional<lignepanier> getLignePanierById(Long id) {
        return lignePanierRepository.findById(id);
    }

    public lignepanier saveLignePanier(lignepanier lp) {
        return lignePanierRepository.save(lp);
    }

    public void deleteLignePanier(Long id) {
        lignePanierRepository.deleteById(id);
    }
}
