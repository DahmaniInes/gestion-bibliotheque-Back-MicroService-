package com.example.livraison.controllers;



import com.example.livraison.entities.Livreur;
import com.example.livraison.services.ILivreurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livreurs")
public class LivreurController {

    private final ILivreurService livreurService;

    @Autowired
    public LivreurController(ILivreurService livreurService) {
        this.livreurService = livreurService;
    }

    @GetMapping
    public ResponseEntity<List<Livreur>> getAllLivreurs() {
        return ResponseEntity.ok(livreurService.getAllLivreurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livreur> getLivreurById(@PathVariable Long id) {
        return livreurService.getLivreurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Livreur> createLivreur(@RequestBody Livreur livreur) {
        Livreur savedLivreur = livreurService.saveLivreur(livreur);
        return ResponseEntity.ok(savedLivreur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livreur> updateLivreur(@PathVariable Long id, @RequestBody Livreur livreur) {
        Livreur updatedLivreur = livreurService.updateLivreur(id, livreur);
        return ResponseEntity.ok(updatedLivreur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivreur(@PathVariable Long id) {
        livreurService.deleteLivreur(id);
        return ResponseEntity.noContent().build();
    }
}

