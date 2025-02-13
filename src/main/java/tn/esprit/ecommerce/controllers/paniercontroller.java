package tn.esprit.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<panier> getAllPaniers() {
        return panierService.getAllPaniers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<panier> getPanierById(@PathVariable Long id) {
        Optional<panier> panier = panierService.getPanierById(id);
        return panier.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public panier createPanier(@RequestBody panier p) {
        return panierService.savePanier(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanier(@PathVariable Long id) {
        panierService.deletePanier(id);
        return ResponseEntity.noContent().build();
    }
}
