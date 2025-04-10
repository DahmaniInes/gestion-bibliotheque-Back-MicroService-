package tn.esprit.bibliogestioncatalogues.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.bibliogestioncatalogues.entities.Livre;
import tn.esprit.bibliogestioncatalogues.services.ILivreService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Tag(name = "Livre", description = "Operations related to books")
@RestController
@RequestMapping("/livres")
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

    @Operation(summary = "Export all books to Excel")
    @GetMapping("/export/excel")
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

    @Operation(summary = "Export all books to PDF")
    @GetMapping("/export/pdf")
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

    @Operation(summary = "Import books from Excel")
    @PostMapping("/import/excel")
    public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile file) {
        try {
            livreService.importLivresFromExcel(file);
            return ResponseEntity.ok("Importation réussie !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur pendant l'importation : " + e.getMessage());
        }
    }
}
