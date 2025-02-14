package com.example.livraison.controllers;


import com.example.livraison.entities.EtatLivraison;
import com.example.livraison.services.IEtatLivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etat-livraisons")
public class EtatLivraisonController {

    private final IEtatLivraisonService etatLivraisonService;

    @Autowired
    public EtatLivraisonController(IEtatLivraisonService etatLivraisonService) {
        this.etatLivraisonService = etatLivraisonService;
    }

    @GetMapping
    public ResponseEntity<List<EtatLivraison>> getAllEtatLivraisons() {
        return ResponseEntity.ok(etatLivraisonService.getAllEtatLivraisons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtatLivraison> getEtatLivraisonById(@PathVariable Long id) {
        return etatLivraisonService.getEtatLivraisonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EtatLivraison> createEtatLivraison(@RequestBody EtatLivraison etatLivraison) {
        EtatLivraison savedEtat = etatLivraisonService.saveEtatLivraison(etatLivraison);
        return ResponseEntity.ok(savedEtat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtatLivraison> updateEtatLivraison(@PathVariable Long id, @RequestBody EtatLivraison etatLivraison) {
        EtatLivraison updatedEtat = etatLivraisonService.updateEtatLivraison(id, etatLivraison);
        return ResponseEntity.ok(updatedEtat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtatLivraison(@PathVariable Long id) {
        etatLivraisonService.deleteEtatLivraison(id);
        return ResponseEntity.noContent().build();
    }
}
