package tn.esprit.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecommerce.entities.panier;
import tn.esprit.ecommerce.repositories.panierrepository;

import java.util.List;
import java.util.Optional;

@Service
public class panierservice {

    @Autowired
    private panierrepository panierRepository;

    // Create
    public panier savePanier(panier panier) {
        return panierRepository.save(panier);
    }

    // Read (Get All)
    public List<panier> getAllPaniers() {
        return panierRepository.findAll();
    }

    // Read (Get by ID)
    public Optional<panier> getPanierById(Long id) {
        return panierRepository.findById(id);
    }

    // Update
    public panier updatePanier(Long id, panier panierDetails) {
        panier panier = panierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("panier not found with id: " + id));

        panier.setClientId(panierDetails.getClientId());
        panier.setPrixTotal(panierDetails.getPrixTotal());
        panier.setStatut(panierDetails.getStatut());

        return panierRepository.save(panier);
    }

    // Delete
    public void deletePanier(Long id) {
        panier panier = panierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("panier not found with id: " + id));
        panierRepository.delete(panier);
    }
}