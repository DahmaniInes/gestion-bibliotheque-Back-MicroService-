package tn.esprit.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecommerce.entities.panier;
import tn.esprit.ecommerce.exceptions.resourcenotfoundexception;
import tn.esprit.ecommerce.repositories.panierrepository;

import java.util.List;
import java.util.Optional;

@Service
public class panierservice {

    @Autowired
    private panierrepository panierrepository;

    // Créer un nouveau panier
    public panier createPanier(panier panier) {
        if (panier.getClientId() == null) {
            throw new IllegalArgumentException("l'id du client est requis");
        }
        if (panier.getPrixTotal() == null || panier.getPrixTotal() < 0) {
            throw new IllegalArgumentException("le prix total doit être positif ou zéro");
        }
        if (panier.getStatut() == null) {
            panier.setStatut(tn.esprit.ecommerce.entities.panier.StatutPanier.ACTIF); // Statut par défaut
        }
        return panierrepository.save(panier);
    }

    // Récupérer tous les paniers
    public List<panier> getAllPaniers() {
        return panierrepository.findAll();
    }

    // Récupérer un panier par ID
    public Optional<panier> getPanierById(Long id) {
        return panierrepository.findById(id);
    }

    // Mettre à jour un panier
    public panier updatePanier(Long id, panier panierDetails) {
        panier panier = panierrepository.findById(id)
                .orElseThrow(() -> new resourcenotfoundexception("panier non trouvé avec l'id: " + id));

        if (panierDetails.getClientId() != null) {
            panier.setClientId(panierDetails.getClientId());
        }
        if (panierDetails.getPrixTotal() != null) {
            if (panierDetails.getPrixTotal() < 0) {
                throw new IllegalArgumentException("le prix total ne peut pas être négatif");
            }
            panier.setPrixTotal(panierDetails.getPrixTotal());
        }
        if (panierDetails.getStatut() != null) {
            panier.setStatut(panierDetails.getStatut());
        }

        return panierrepository.save(panier);
    }

    // Supprimer un panier
    public void deletePanier(Long id) {
        panier panier = panierrepository.findById(id)
                .orElseThrow(() -> new resourcenotfoundexception("panier non trouvé avec l'id: " + id));
        panierrepository.delete(panier);
    }
}