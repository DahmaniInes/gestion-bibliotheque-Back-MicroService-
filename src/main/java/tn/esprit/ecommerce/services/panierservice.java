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

    public List<panier> getAllPaniers() {
        return panierRepository.findAll();
    }

    public Optional<panier> getPanierById(Long id) {
        return panierRepository.findById(id);
    }

    public panier savePanier(panier p) {
        return panierRepository.save(p);
    }

    public void deletePanier(Long id) {
        panierRepository.deleteById(id);
    }
}