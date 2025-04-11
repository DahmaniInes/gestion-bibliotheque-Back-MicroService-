package tn.esprit.ecommerce.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecommerce.entities.panier;
import tn.esprit.ecommerce.services.panierservice;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paniers")
public class paniercontroller {

    @Autowired
    private panierservice panierservice;

    // Créer un nouveau panier
    @PostMapping
    public ResponseEntity<panier> createPanier(@Valid @RequestBody panier panier) {
        panier createdPanier = panierservice.createPanier(panier);
        return new ResponseEntity<>(createdPanier, HttpStatus.CREATED);
    }

    // Récupérer tous les paniers
    @GetMapping
    public ResponseEntity<List<panier>> getAllPaniers() {
        List<panier> paniers = panierservice.getAllPaniers();
        return new ResponseEntity<>(paniers, HttpStatus.OK);
    }

    // Récupérer un panier par ID
    @GetMapping("/{id}")
    public ResponseEntity<panier> getPanierById(@PathVariable Long id) {
        Optional<panier> panier = panierservice.getPanierById(id);
        return panier.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Mettre à jour un panier
    @PutMapping("/{id}")
    public ResponseEntity<panier> updatePanier(@PathVariable Long id, @Valid @RequestBody panier panierDetails) {
        try {
            panier updatedPanier = panierservice.updatePanier(id, panierDetails);
            return new ResponseEntity<>(updatedPanier, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Supprimer un panier
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanier(@PathVariable Long id) {
        try {
            panierservice.deletePanier(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}