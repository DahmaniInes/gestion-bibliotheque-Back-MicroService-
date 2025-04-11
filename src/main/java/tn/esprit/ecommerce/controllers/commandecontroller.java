package tn.esprit.ecommerce.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecommerce.entities.commande;
import tn.esprit.ecommerce.services.commandeservice;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/commandes")
public class commandecontroller {

    @Autowired
    private commandeservice commandeservice;

    // Créer une nouvelle commande
    @PostMapping
    public ResponseEntity<commande> createCommande(@Valid @RequestBody commande commande) {
        commande createdCommande = commandeservice.createCommande(commande);
        return new ResponseEntity<>(createdCommande, HttpStatus.CREATED);
    }

    // Récupérer toutes les commandes
    @GetMapping
    public ResponseEntity<List<commande>> getAllCommandes() {
        List<commande> commandes = commandeservice.getAllCommandes();
        return new ResponseEntity<>(commandes, HttpStatus.OK);
    }

    // Récupérer une commande par ID
    @GetMapping("/{id}")
    public ResponseEntity<commande> getCommandeById(@PathVariable Long id) {
        Optional<commande> commande = commandeservice.getCommandeById(id);
        return commande.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Mettre à jour une commande
    @PutMapping("/{id}")
    public ResponseEntity<commande> updateCommande(@PathVariable Long id, @Valid @RequestBody commande commandeDetails) {
        try {
            commande updatedCommande = commandeservice.updateCommande(id, commandeDetails);
            return new ResponseEntity<>(updatedCommande, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Supprimer une commande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        try {
            commandeservice.deleteCommande(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}