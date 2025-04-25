package tn.esprit.bibliogestioncatalogues.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Import pour la sécurité
import org.springframework.web.bind.annotation.*;
import tn.esprit.bibliogestioncatalogues.entities.Auteur;
import tn.esprit.bibliogestioncatalogues.services.IAuteurService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Auteur", description = "Opérations liées à la gestion des auteurs")
@RestController
@RequestMapping("/categories/auteurs")
public class AuteurController {

    @Autowired
    private IAuteurService auteurService;

    @Operation(summary = "Récupérer tous les auteurs", description = "Retourne la liste complète des auteurs enregistrés dans la base de données.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des auteurs récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @GetMapping
    @PreAuthorize("hasRole('user')") // Accessible aux utilisateurs avec le rôle 'user'
    public ResponseEntity<List<Auteur>> getAllAuteurs() {
        List<Auteur> auteurs = auteurService.getAllAuteurs();
        return new ResponseEntity<>(auteurs, HttpStatus.OK);
    }

    @Operation(summary = "Récupérer un auteur par ID", description = "Retourne les détails d'un auteur spécifique en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Auteur trouvé et retourné avec succès"),
            @ApiResponse(responseCode = "404", description = "Auteur non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('user')") // Accessible aux utilisateurs avec le rôle 'user'
    public ResponseEntity<Auteur> getAuteurById(
            @PathVariable @Parameter(description = "ID de l'auteur à récupérer", example = "1") Long id) {
        Optional<Auteur> auteur = auteurService.getAuteurById(id);
        return auteur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Ajouter un nouvel auteur", description = "Crée un nouvel auteur avec les informations fournies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Auteur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @PostMapping
    @PreAuthorize("hasRole('admin')") // Réservé aux administrateurs
    public ResponseEntity<Auteur> createAuteur(
            @RequestBody @Parameter(description = "Détails de l'auteur à créer") Auteur auteur) {
        auteur.setId(null); // Assurez-vous que l'ID est null pour une nouvelle entité
        Auteur savedAuteur = auteurService.saveAuteur(auteur);
        return new ResponseEntity<>(savedAuteur, HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour un auteur existant", description = "Met à jour les informations d'un auteur spécifique en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Auteur mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Auteur non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')") // Réservé aux administrateurs
    public ResponseEntity<Auteur> updateAuteur(
            @PathVariable @Parameter(description = "ID de l'auteur à mettre à jour", example = "1") Long id,
            @RequestBody @Parameter(description = "Nouvelles informations de l'auteur") Auteur auteur) {
        Auteur updatedAuteur = auteurService.updateAuteur(id, auteur);
        return updatedAuteur != null ? ResponseEntity.ok(updatedAuteur) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Supprimer un auteur", description = "Supprime un auteur spécifique en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Auteur supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Auteur non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')") // Réservé aux administrateurs
    public ResponseEntity<Void> deleteAuteur(
            @PathVariable @Parameter(description = "ID de l'auteur à supprimer", example = "1") Long id) {
        auteurService.deleteAuteur(id);
        return ResponseEntity.noContent().build();
    }
}