package tn.esprit.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecommerce.entities.lignepanier;
import tn.esprit.ecommerce.exceptions.resourcenotfoundexception;
import tn.esprit.ecommerce.repositories.lignepanierrepository;

import java.util.List;
import java.util.Optional;

@Service
public class lignepanierservice {

    @Autowired
    private lignepanierrepository lignepanierrepository;

    // Créer une nouvelle ligne de panier
    public lignepanier createLignePanier(lignepanier lignepanier) {
        if (lignepanier.getPanierId() == null) {
            throw new IllegalArgumentException("l'id du panier est requis");
        }
        if (lignepanier.getProduitId() == null) {
            throw new IllegalArgumentException("l'id du produit est requis");
        }
        if (lignepanier.getQuantite() == null || lignepanier.getQuantite() <= 0) {
            throw new IllegalArgumentException("la quantité doit être positive");
        }
        return lignepanierrepository.save(lignepanier);
    }

    // Récupérer toutes les lignes de panier
    public List<lignepanier> getAllLignePaniers() {
        return lignepanierrepository.findAll();
    }

    // Récupérer une ligne de panier par ID
    public Optional<lignepanier> getLignePanierById(Long id) {
        return lignepanierrepository.findById(id);
    }

    // Mettre à jour une ligne de panier
    public lignepanier updateLignePanier(Long id, lignepanier lignepanierDetails) {
        lignepanier lignepanier = lignepanierrepository.findById(id)
                .orElseThrow(() -> new resourcenotfoundexception("lignepanier non trouvée avec l'id: " + id));

        if (lignepanierDetails.getPanierId() != null) {
            lignepanier.setPanierId(lignepanierDetails.getPanierId());
        }
        if (lignepanierDetails.getProduitId() != null) {
            lignepanier.setProduitId(lignepanierDetails.getProduitId());
        }
        if (lignepanierDetails.getQuantite() != null) {
            if (lignepanierDetails.getQuantite() <= 0) {
                throw new IllegalArgumentException("la quantité doit être positive");
            }
            lignepanier.setQuantite(lignepanierDetails.getQuantite());
        }

        return lignepanierrepository.save(lignepanier);
    }

    // Supprimer une ligne de panier
    public void deleteLignePanier(Long id) {
        lignepanier lignepanier = lignepanierrepository.findById(id)
                .orElseThrow(() -> new resourcenotfoundexception("lignepanier non trouvée avec l'id: " + id));
        lignepanierrepository.delete(lignepanier);
    }
}