package tn.esprit.ecommerce.controllers;

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

    // Create a new commande
    @PostMapping
    public ResponseEntity<commande> createCommande(@RequestBody commande commande) {
        commande createdCommande = commandeservice.createCommande(commande);
        return new ResponseEntity<>(createdCommande, HttpStatus.CREATED);
    }

    // Get all commandes
    @GetMapping
    public ResponseEntity<List<commande>> getAllCommandes() {
        List<commande> commandes = commandeservice.getAllCommandes();
        return new ResponseEntity<>(commandes, HttpStatus.OK);
    }

    // Get commande by ID
    @GetMapping("/{id}")
    public ResponseEntity<commande> getCommandeById(@PathVariable Long id) {
        Optional<commande> commande = commandeservice.getCommandeById(id);
        return commande.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update commande
    @PutMapping("/{id}")
    public ResponseEntity<commande> updateCommande(@PathVariable Long id, @RequestBody commande commandeDetails) {
        try {
            commande updatedCommande = commandeservice.updateCommande(id, commandeDetails);
            return new ResponseEntity<>(updatedCommande, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete commande
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