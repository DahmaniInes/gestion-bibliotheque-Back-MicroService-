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
import tn.esprit.bibliogestioncatalogues.entities.Categorie;
import tn.esprit.bibliogestioncatalogues.entities.Livre;
import tn.esprit.bibliogestioncatalogues.services.ICategorieService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Categorie", description = "Opérations liées à la gestion des catégories")
@RestController
@RequestMapping("/categories") // Simplifié pour éviter la redondance
public class CategorieController {

    @Autowired
    private ICategorieService categorieService;

    @Operation(summary = "Récupérer toutes les catégories", description = "Retourne la liste complète des catégories enregistrées dans la base de données.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des catégories récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @GetMapping
    @PreAuthorize("hasRole('user')") // Accessible aux utilisateurs avec le rôle 'user'
    public ResponseEntity<List<Categorie>> getAllCategories() {
        List<Categorie> categories = categorieService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Operation(summary = "Lister les livres d'une catégorie", description = "Retourne la liste des livres appartenant à une catégorie spécifique, identifiée par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des livres récupérée avec succès"),
            @ApiResponse(responseCode = "404", description = "Catégorie non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @GetMapping("/{id}/livres")
    @PreAuthorize("hasRole('user')") // Accessible aux utilisateurs avec le rôle 'user'
    public ResponseEntity<List<Livre>> getLivresByCategorie(
            @PathVariable @Parameter(description = "ID de la catégorie", example = "1") Long id) {
        List<Livre> livres = categorieService.getLivresByCategorie(id);
        return ResponseEntity.ok(livres);
    }

    @Operation(summary = "Récupérer une catégorie par ID", description = "Retourne les détails d'une catégorie spécifique en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Catégorie trouvée et retournée avec succès"),
            @ApiResponse(responseCode = "404", description = "Catégorie non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('user')") // Accessible aux utilisateurs avec le rôle 'user'
    public ResponseEntity<Categorie> getCategorieById(
            @PathVariable @Parameter(description = "ID de la catégorie à récupérer", example = "1") Long id) {
        Optional<Categorie> categorie = categorieService.getCategorieById(id);
        return categorie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Ajouter une nouvelle catégorie", description = "Crée une nouvelle catégorie avec les informations fournies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Catégorie créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @PostMapping
    @PreAuthorize("hasRole('admin')") // Réservé aux administrateurs
    public ResponseEntity<Categorie> createCategorie(
            @RequestBody @Parameter(description = "Détails de la catégorie à créer") Categorie categorie) {
        categorie.setId(null); // Assurez-vous que l'ID est null pour une nouvelle entité
        Categorie savedCategorie = categorieService.saveCategorie(categorie);
        return new ResponseEntity<>(savedCategorie, HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour une catégorie existante", description = "Met à jour les informations d'une catégorie spécifique en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Catégorie mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Catégorie non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')") // Réservé aux administrateurs
    public ResponseEntity<Categorie> updateCategorie(
            @PathVariable @Parameter(description = "ID de la catégorie à mettre à jour", example = "1") Long id,
            @RequestBody @Parameter(description = "Nouvelles informations de la catégorie") Categorie categorie) {
        Categorie updatedCategorie = categorieService.updateCategorie(id, categorie);
        return updatedCategorie != null ? ResponseEntity.ok(updatedCategorie) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Supprimer une catégorie", description = "Supprime une catégorie spécifique en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Catégorie supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Catégorie non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')") // Réservé aux administrateurs
    public ResponseEntity<Void> deleteCategorie(
            @PathVariable @Parameter(description = "ID de la catégorie à supprimer", example = "1") Long id) {
        categorieService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }
}