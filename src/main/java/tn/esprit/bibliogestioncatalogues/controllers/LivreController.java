package tn.esprit.bibliogestioncatalogues.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.bibliogestioncatalogues.entities.Livre;
import tn.esprit.bibliogestioncatalogues.services.ILivreService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Livre", description = "Operations related to books")
@RestController
@RequestMapping("/api/livres")
public class LivreController {

    @Autowired
    private ILivreService livreService;

    @Operation(summary = "Get all books")
    @GetMapping
    public ResponseEntity<List<Livre>> getAllLivres() {
        List<Livre> livres = livreService.getAllLivres();
        return new ResponseEntity<>(livres, HttpStatus.OK);
    }

    @Operation(summary = "Get a book by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreById(@PathVariable Long id) {
        Optional<Livre> livre = livreService.getLivreById(id);
        return livre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new book")
    @PostMapping
    public ResponseEntity<Livre> createLivre(@RequestBody Livre livre) {
        Livre savedLivre = livreService.saveLivre(livre);
        return new ResponseEntity<>(savedLivre, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing book")
    @PutMapping("/{id}")
    public ResponseEntity<Livre> updateLivre(@PathVariable Long id, @RequestBody Livre livre) {
        Livre updatedLivre = livreService.updateLivre(id, livre);
        return updatedLivre != null ? ResponseEntity.ok(updatedLivre) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a book")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivre(@PathVariable Long id) {
        livreService.deleteLivre(id);
        return ResponseEntity.noContent().build();
    }
}
