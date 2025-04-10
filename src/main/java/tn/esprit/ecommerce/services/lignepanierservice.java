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
    private lignepanierrepository lignepanierrepository;

    // Create
    public lignepanier createLignePanier(lignepanier lignepanier) {
        return lignepanierrepository.save(lignepanier);
    }

    // Read (Get All)
    public List<lignepanier> getAllLignePaniers() {
        return lignepanierrepository.findAll();
    }

    // Read (Get by ID)
    public Optional<lignepanier> getLignePanierById(Long id) {
        return lignepanierrepository.findById(id);
    }

    // Update
    public lignepanier updateLignePanier(Long id, lignepanier lignepanierDetails) {
        lignepanier lignepanier = lignepanierrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("lignepanier not found with id: " + id));

        lignepanier.setPanierId(lignepanierDetails.getPanierId());
        lignepanier.setProduitId(lignepanierDetails.getProduitId());
        lignepanier.setQuantite(lignepanierDetails.getQuantite());

        return lignepanierrepository.save(lignepanier);
    }

    // Delete
    public void deleteLignePanier(Long id) {
        lignepanier lignepanier = lignepanierrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("lignepanier not found with id: " + id));
        lignepanierrepository.delete(lignepanier);
    }
}