package com.example.livraison.controllers;


import com.example.livraison.entities.Livraison;
import com.example.livraison.services.EmailService;
import com.example.livraison.services.ILivraisonService;
import com.example.livraison.services.LivraisonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/livraisons")
public class LivraisonController {
    @Autowired
    private final ILivraisonService livraisonService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/from-commande/{commandeId}")
    public Livraison creerDepuisCommande(@PathVariable Long commandeId) {
        return livraisonService.creerLivraisonDepuisCommande(commandeId);
    }

    @Autowired
    public LivraisonController(ILivraisonService livraisonService) {
        this.livraisonService = livraisonService;
    }

//    @GetMapping
//    public ResponseEntity<List<Livraison>> getAllLivraisons() {
//        return ResponseEntity.ok(livraisonService.getAllLivraisons());
//    }
    @GetMapping
    public ResponseEntity<Page<Livraison>> getAllLivraisons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort[1]), sort[0]));
        Page<Livraison> livraisons = livraisonService.getAllLivraisonsPaginated(pageable);
        return ResponseEntity.ok(livraisons);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generateLivraisonPdf(@PathVariable Long id) {
        byte[] pdf = livraisonService.generatePdfForLivraison(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("livraison_" + id + ".pdf").build());
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
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
        String livreurEmail = livraison.getLivreur().getEmail();
        String subject = "Nouvelle Livraison Assignée";
        String message = "Bonjour " + livraison.getLivreur().getPrenom() + ",\n\n" +
                "Une nouvelle livraison vous a été assignée.\n" +
                "Adresse: " + livraison.getAdresseLivraison() + "\n" +
                "Téléphone du client: " + livraison.getTelephoneClient();

        emailService.sendEmail(livreurEmail, subject, message);

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
