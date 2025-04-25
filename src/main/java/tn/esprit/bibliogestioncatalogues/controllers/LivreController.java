package tn.esprit.bibliogestioncatalogues.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Import pour la sécurité
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.bibliogestioncatalogues.entities.Commentaire;
import tn.esprit.bibliogestioncatalogues.entities.Livre;
import tn.esprit.bibliogestioncatalogues.services.ILivreService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Tag(name = "Livre", description = "Opérations liées à la gestion des livres")
@RestController
@RequestMapping("/categories/livres")
public class LivreController {

    @Autowired
    private ILivreService livreService;

    @Operation(summary = "Récupérer tous les livres", description = "Retourne la liste complète des livres enregistrés dans la base de données.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des livres récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @GetMapping
    @PreAuthorize("hasRole('user')") // Accessible uniquement aux utilisateurs avec le rôle 'user'
    public ResponseEntity<List<Livre>> getAllLivres() {
        List<Livre> livres = livreService.getAllLivres();
        return new ResponseEntity<>(livres, HttpStatus.OK);
    }

    @Operation(summary = "Récupérer un livre par ID", description = "Retourne les détails d'un livre spécifique en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livre trouvé et retourné avec succès"),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('user')") // Accessible uniquement aux utilisateurs avec le rôle 'user'
    public ResponseEntity<Livre> getLivreById(
            @PathVariable @Parameter(description = "ID du livre à récupérer", example = "1") Long id) {
        Optional<Livre> livre = livreService.getLivreById(id);
        return livre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Ajouter un nouveau livre", description = "Crée un nouveau livre avec les informations fournies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livre créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @PostMapping
    @PreAuthorize("hasRole('admin')") // Réservé aux administrateurs
    public ResponseEntity<Livre> createLivre(@RequestBody Livre livre) {
        livre.setId(null); // Assurez-vous que l'ID est null pour une nouvelle entité
        Livre savedLivre = livreService.saveLivre(livre);
        return new ResponseEntity<>(savedLivre, HttpStatus.CREATED);
    }

    @Operation(summary = "Mettre à jour un livre existant", description = "Met à jour les informations d'un livre spécifique en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livre mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')") // Réservé aux administrateurs
    public ResponseEntity<Livre> updateLivre(
            @PathVariable @Parameter(description = "ID du livre à mettre à jour", example = "1") Long id,
            @RequestBody @Parameter(description = "Nouvelles informations du livre") Livre livre) {
        Livre updatedLivre = livreService.updateLivre(id, livre);
        return updatedLivre != null ? ResponseEntity.ok(updatedLivre) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Supprimer un livre", description = "Supprime un livre spécifique en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livre supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')") // Réservé aux administrateurs
    public ResponseEntity<Void> deleteLivre(
            @PathVariable @Parameter(description = "ID du livre à supprimer", example = "1") Long id) {
        livreService.deleteLivre(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Exporter tous les livres en Excel", description = "Génère un fichier Excel contenant la liste de tous les livres.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fichier Excel généré et téléchargé avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne lors de l'exportation")
    })
    @GetMapping("/export/excel")
    @PreAuthorize("hasRole('admin')") // Réservé aux administrateurs
    public ResponseEntity<InputStreamResource> exportExcel() throws IOException {
        List<Livre> livres = livreService.getAllLivres();
        ByteArrayInputStream in = livreService.exportLivresToExcel(livres);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=livres.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }

    @Operation(summary = "Exporter tous les livres en PDF", description = "Génère un fichier PDF contenant la liste de tous les livres.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fichier PDF généré et téléchargé avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne lors de l'exportation")
    })
    @GetMapping("/export/pdf")
    @PreAuthorize("hasRole('admin')") // Réservé aux administrateurs
    public ResponseEntity<InputStreamResource> exportPdf() {
        List<Livre> livres = livreService.getAllLivres();
        ByteArrayInputStream in = livreService.exportLivresToPdf(livres);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=livres.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(in));
    }

    @Operation(summary = "Importer des livres depuis un fichier Excel", description = "Importe une liste de livres à partir d'un fichier Excel fourni.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Importation réussie"),
            @ApiResponse(responseCode = "400", description = "Fichier invalide ou données incorrectes"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne lors de l'importation")
    })
    @PostMapping("/import/excel")
    @PreAuthorize("hasRole('admin')") // Réservé aux administrateurs
    public ResponseEntity<String> importExcel(
            @RequestParam("file") @Parameter(description = "Fichier Excel contenant les livres à importer") MultipartFile file) {
        try {
            livreService.importLivresFromExcel(file);
            return ResponseEntity.ok("Importation réussie !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur pendant l'importation : " + e.getMessage());
        }
    }

    @Operation(summary = "Rechercher des livres avec des filtres avancés",
            description = "Permet de rechercher des livres en fonction de plusieurs critères : titre, auteur, catégorie, année de publication (plage), et disponibilité.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des livres correspondant aux critères"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @GetMapping("/search")
    @PreAuthorize("hasRole('user')") // Accessible aux utilisateurs avec le rôle 'user'
    public ResponseEntity<List<Livre>> searchLivres(
            @RequestParam(required = false) @Parameter(description = "Titre du livre (recherche partielle, insensible à la casse)", example = "Prince") String titre,
            @RequestParam(required = false) @Parameter(description = "Nom de l'auteur (recherche partielle, insensible à la casse)", example = "Saint-Exupéry") String auteur,
            @RequestParam(required = false) @Parameter(description = "ID de la catégorie", example = "1") Long categorieId,
            @RequestParam(required = false) @Parameter(description = "Année minimum de publication", example = "1940") Integer anneeMin,
            @RequestParam(required = false) @Parameter(description = "Année maximum de publication", example = "1950") Integer anneeMax,
            @RequestParam(required = false) @Parameter(description = "Filtrer uniquement les livres disponibles (stock > 0)", example = "true") Boolean disponible) {
        List<Livre> livres = livreService.searchLivres(titre, auteur, categorieId, anneeMin, anneeMax, disponible);
        return ResponseEntity.ok(livres);
    }

    @Operation(summary = "Ajouter un commentaire et une note à un livre",
            description = "Permet à un utilisateur d'ajouter un commentaire et une note (de 1 à 5) pour un livre spécifique.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Commentaire ajouté avec succès"),
            @ApiResponse(responseCode = "404", description = "Livre ou utilisateur non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @PostMapping("/{id}/commentaires")
    @PreAuthorize("hasRole('user')") // Accessible aux utilisateurs avec le rôle 'user'
    public ResponseEntity<Commentaire> addCommentaire(
            @PathVariable @Parameter(description = "ID du livre", example = "1") Long id,
            @RequestBody @Parameter(description = "Détails du commentaire (note et texte)") Commentaire commentaire,
            @RequestParam @Parameter(description = "ID de l'utilisateur qui ajoute le commentaire", example = "1") Long utilisateurId) {
        Commentaire savedCommentaire = livreService.addCommentaire(id, utilisateurId, commentaire);
        return new ResponseEntity<>(savedCommentaire, HttpStatus.CREATED);
    }

    @Operation(summary = "Lister les commentaires d'un livre",
            description = "Récupère tous les commentaires et notes associés à un livre spécifique.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des commentaires récupérée avec succès"),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur interne")
    })
    @GetMapping("/{id}/commentaires")
    @PreAuthorize("hasRole('user')") // Accessible aux utilisateurs avec le rôle 'user'
    public ResponseEntity<List<Commentaire>> getCommentairesByLivre(
            @PathVariable @Parameter(description = "ID du livre", example = "1") Long id) {
        List<Commentaire> commentaires = livreService.getCommentairesByLivre(id);
        return ResponseEntity.ok(commentaires);
    }
}