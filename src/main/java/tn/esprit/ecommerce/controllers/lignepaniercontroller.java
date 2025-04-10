package tn.esprit.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecommerce.entities.lignepanier;
import tn.esprit.ecommerce.services.lignepanierservice;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lignepanier")
public class lignepaniercontroller {

    @Autowired
    private lignepanierservice lignepanierservice;

    // Create a new lignepanier
    @PostMapping
    public ResponseEntity<lignepanier> createLignePanier(@RequestBody lignepanier lignepanier) {
        lignepanier createdLignePanier = lignepanierservice.createLignePanier(lignepanier);
        return new ResponseEntity<>(createdLignePanier, HttpStatus.CREATED);
    }

    // Get all lignepaniers
    @GetMapping
    public ResponseEntity<List<lignepanier>> getAllLignePaniers() {
        List<lignepanier> lignepaniers = lignepanierservice.getAllLignePaniers();
        return new ResponseEntity<>(lignepaniers, HttpStatus.OK);
    }

    // Get lignepanier by ID
    @GetMapping("/{id}")
    public ResponseEntity<lignepanier> getLignePanierById(@PathVariable Long id) {
        Optional<lignepanier> lignepanier = lignepanierservice.getLignePanierById(id);
        return lignepanier.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update lignepanier
    @PutMapping("/{id}")
    public ResponseEntity<lignepanier> updateLignePanier(@PathVariable Long id, @RequestBody lignepanier lignepanierDetails) {
        try {
            lignepanier updatedLignePanier = lignepanierservice.updateLignePanier(id, lignepanierDetails);
            return new ResponseEntity<>(updatedLignePanier, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete lignepanier
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLignePanier(@PathVariable Long id) {
        try {
            lignepanierservice.deleteLignePanier(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}