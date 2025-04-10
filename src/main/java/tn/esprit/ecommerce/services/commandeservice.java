package tn.esprit.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.ecommerce.entities.commande;
import tn.esprit.ecommerce.repositories.commanderepository;

import java.util.List;
import java.util.Optional;

@Service
public class commandeservice {

    @Autowired
    private commanderepository commanderepository;

    // Create
    public commande createCommande(commande commande) {
        return commanderepository.save(commande);
    }

    // Read (Get All)
    public List<commande> getAllCommandes() {
        return commanderepository.findAll();
    }

    // Read (Get by ID)
    public Optional<commande> getCommandeById(Long id) {
        return commanderepository.findById(id);
    }

    // Update
    public commande updateCommande(Long id, commande commandeDetails) {
        commande commande = commanderepository.findById(id)
                .orElseThrow(() -> new RuntimeException("commande not found with id: " + id));

        commande.setPanierId(commandeDetails.getPanierId());
        commande.setAdress(commandeDetails.getAdress());
        commande.setDateC(commandeDetails.getDateC());
        commande.setStatut(commandeDetails.getStatut());
        commande.setMethodePaiement(commandeDetails.getMethodePaiement());
        commande.setPhone(commandeDetails.getPhone());

        return commanderepository.save(commande);
    }

    // Delete
    public void deleteCommande(Long id) {
        commande commande = commanderepository.findById(id)
                .orElseThrow(() -> new RuntimeException("commande not found with id: " + id));
        commanderepository.delete(commande);
    }
}