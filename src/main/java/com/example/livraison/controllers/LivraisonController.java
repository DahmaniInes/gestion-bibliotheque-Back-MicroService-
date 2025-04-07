package com.example.livraison.controllers;


import com.example.livraison.entities.Livraison;
import com.example.livraison.services.ILivraisonService;
import com.example.livraison.services.LivraisonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livraisons")
public class LivraisonController {
@Autowired
    private final ILivraisonService livraisonService;
    @PostMapping("/from-commande/{commandeId}")
    public Livraison creerDepuisCommande(@PathVariable Long commandeId) {
        return livraisonService.creerLivraisonDepuisCommande(commandeId);
    }

    @Autowired
    public LivraisonController(ILivraisonService livraisonService) {
        this.livraisonService = livraisonService;
    }

    @GetMapping
    public ResponseEntity<List<Livraison>> getAllLivraisons() {
        return ResponseEntity.ok(livraisonService.getAllLivraisons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livraison> getLivraisonById(@PathVariable Long id) {
        return livraisonService.getLivraisonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Livraison> createLivraison(@RequestBody Livraison livraison) {
        Livraison savedLivraison = livraisonService.saveLivraison(livraison);
        return new ResponseEntity<>(savedLivraison, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livraison> updateLivraison(@PathVariable Long id, @RequestBody Livraison livraison) {
        Livraison updatedLivraison = livraisonService.updateLivraison(id, livraison);
        return ResponseEntity.ok(updatedLivraison);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivraison(@PathVariable Long id) {
        livraisonService.deleteLivraison(id);
        return ResponseEntity.noContent().build();
    }
}
