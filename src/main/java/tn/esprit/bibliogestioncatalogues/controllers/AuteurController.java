package tn.esprit.bibliogestioncatalogues.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.bibliogestioncatalogues.entities.Auteur;
import tn.esprit.bibliogestioncatalogues.services.IAuteurService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Auteur", description = "Operations related to authors")
@RestController
@RequestMapping("/api/auteurs")
public class AuteurController {

    @Autowired
    private IAuteurService auteurService;

    @Operation(summary = "Get all authors")
    @GetMapping
    public ResponseEntity<List<Auteur>> getAllAuteurs() {
        List<Auteur> auteurs = auteurService.getAllAuteurs();
        return new ResponseEntity<>(auteurs, HttpStatus.OK);
    }

    @Operation(summary = "Get an author by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Auteur> getAuteurById(@PathVariable Long id) {
        Optional<Auteur> auteur = auteurService.getAuteurById(id);
        return auteur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new author")
    @PostMapping
    public ResponseEntity<Auteur> createAuteur(@RequestBody Auteur auteur) {
        Auteur savedAuteur = auteurService.saveAuteur(auteur);
        return new ResponseEntity<>(savedAuteur, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing author")
    @PutMapping("/{id}")
    public ResponseEntity<Auteur> updateAuteur(@PathVariable Long id, @RequestBody Auteur auteur) {
        Auteur updatedAuteur = auteurService.updateAuteur(id, auteur);
        return updatedAuteur != null ? ResponseEntity.ok(updatedAuteur) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete an author")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuteur(@PathVariable Long id) {
        auteurService.deleteAuteur(id);
        return ResponseEntity.noContent().build();
    }
}
