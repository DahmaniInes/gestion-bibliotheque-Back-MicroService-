package tn.esprit.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ecommerce.entities.commande;
import tn.esprit.ecommerce.services.commandeservice;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commandes")
public class commandecontroller {
    @Autowired
    private commandeservice commandeService;

    @GetMapping
    public List<commande> getAllCommandes() {
        return commandeService.getAllCommandes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<commande> getCommandeById(@PathVariable Long id) {
        Optional<commande> commande = commandeService.getCommandeById(id);
        return commande.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public commande createCommande(@RequestBody commande cmd) {
        return commandeService.saveCommande(cmd);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }
}