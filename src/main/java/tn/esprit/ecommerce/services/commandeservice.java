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
    private commanderepository commandeRepository;

    public List<commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public Optional<commande> getCommandeById(Long id) {
        return commandeRepository.findById(id);
    }

    public commande saveCommande(commande cmd) {
        return commandeRepository.save(cmd);
    }

    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }
}