package tn.esprit.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecommerce.entities.lignepanier;
import tn.esprit.ecommerce.services.lignepanierservice;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lignepaniers")
public class lignepaniercontroller {
    @Autowired
    private lignepanierservice lignePanierService;

    @GetMapping
    public List<lignepanier> getAllLignePaniers() {
        return lignePanierService.getAllLignePaniers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<lignepanier> getLignePanierById(@PathVariable Long id) {
        Optional<lignepanier> lignePanier = lignePanierService.getLignePanierById(id);
        return lignePanier.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public lignepanier createLignePanier(@RequestBody lignepanier lp) {
        return lignePanierService.saveLignePanier(lp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLignePanier(@PathVariable Long id) {
        lignePanierService.deleteLignePanier(id);
        return ResponseEntity.noContent().build();
    }
}