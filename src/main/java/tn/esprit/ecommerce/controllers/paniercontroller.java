package tn.esprit.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecommerce.entities.panier;
import tn.esprit.ecommerce.services.panierservice;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paniers")
public class paniercontroller {

    @Autowired
    private panierservice panierService;

    // Create a new panier
    @PostMapping
    public ResponseEntity<panier> createPanier(@RequestBody panier panier) {
        panier createdPanier = panierService.savePanier(panier);
        return new ResponseEntity<>(createdPanier, HttpStatus.CREATED);
    }

    // Get all paniers
    @GetMapping
    public ResponseEntity<List<panier>> getAllPaniers() {
        List<panier> paniers = panierService.getAllPaniers();
        return new ResponseEntity<>(paniers, HttpStatus.OK);
    }

    // Get panier by ID
    @GetMapping("/{id}")
    public ResponseEntity<panier> getPanierById(@PathVariable Long id) {
        Optional<panier> panier = panierService.getPanierById(id);
        return panier.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update panier
    @PutMapping("/{id}")
    public ResponseEntity<panier> updatePanier(@PathVariable Long id, @RequestBody panier panierDetails) {
        try {
            panier updatedPanier = panierService.updatePanier(id, panierDetails);
            return new ResponseEntity<>(updatedPanier, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete panier
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanier(@PathVariable Long id) {
        try {
            panierService.deletePanier(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}