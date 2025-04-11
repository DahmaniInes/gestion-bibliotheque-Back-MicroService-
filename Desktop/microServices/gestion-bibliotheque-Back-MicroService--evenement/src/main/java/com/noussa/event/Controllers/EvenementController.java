package com.noussa.event.Controllers;

import com.noussa.event.Entities.Evenement;
import com.noussa.event.Entities.NotificationRequest;

import com.noussa.event.Services.EmailService;
import com.noussa.event.Services.EvenementService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/evenement") // Convention REST : pluriel et préfixe /api
//@AllArgsConstructor
public class EvenementController {

    private final EvenementService evenementService;

    private final EmailService emailService;

    @Autowired
    public EvenementController(EvenementService evenementService, EmailService emailService) {
        this.evenementService = evenementService;
        this.emailService = emailService;
    }
    // 🔹 Créer un événement
    @PostMapping
    public ResponseEntity<Evenement> createEvent(@RequestBody Evenement evenement) {
        try {
            Evenement savedEvent = evenementService.createEvent(evenement);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
        } catch (Exception e) {
            // Ajoute des détails sur l'erreur pour le débogage
            System.out.println("Erreur lors de la création de l'événement : " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 🔹 Lire tous les événements
    @GetMapping
    public ResponseEntity<List<Evenement>> getAllEvents() {
        List<Evenement> evenements = evenementService.getAllEvents();
        return ResponseEntity.ok(evenements);
    }

    // 🔹 Lire un événement par ID
    @GetMapping("/{id}")
    public ResponseEntity<Evenement> getEventById(@PathVariable Long id) {
        return evenementService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Mettre à jour un événement
    @PutMapping("/{id}")
    public ResponseEntity<Evenement> updateEvent(@PathVariable Long id, @RequestBody Evenement updatedEvent) {
        try {
            Evenement evenement = evenementService.updateEvent(id, updatedEvent);
            if (evenement == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(evenement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 🔹 Supprimer un événement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        evenementService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Envoyer une notification par email
    @PostMapping("/{id}/notify")
    public ResponseEntity<String> sendNotification(@PathVariable Long id, @RequestBody NotificationRequest request) {
        return evenementService.getEventById(id)
                .map(evenement -> {
                    // Personnaliser le message avec les détails de l'événement
                    String message = request.getMessage() + "\n\nDétails de l'événement :\n" +
                            "Titre : " + evenement.getTitle() + "\n" +
                            "Date : " + evenement.getEventDate() + "\n" +
                            "Lieu : " + evenement.getLocation();

                    NotificationRequest enrichedRequest = new NotificationRequest(
                            request.getRecipientEmail(),
                            request.getSubject(),
                            message
                    );

                    try {
                        emailService.sendEmail(enrichedRequest);
                        return ResponseEntity.ok("Notification envoyée avec succès");
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Erreur lors de l'envoi : " + e.getMessage());
                    }
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Événement non trouvé"));
    }


    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadResourceFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        System.out.println("Début de uploadResourceFile pour l'ID : " + id);
        System.out.println("Fichier reçu : " + (file != null ? file.getOriginalFilename() : "null"));
        if (file == null || file.isEmpty()) {
            System.out.println("Erreur : Aucun fichier reçu ou fichier vide");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aucun fichier reçu ou fichier vide");
        }
        System.out.println("Taille du fichier : " + file.getSize() + " bytes");
        System.out.println("Type de contenu du fichier : " + file.getContentType());

        return evenementService.getEventById(id)
                .map(evenement -> {
                    System.out.println("Événement trouvé pour l'ID " + id + " : " + evenement.getTitle());
                    try {
                        byte[] fileBytes = file.getBytes();
                        System.out.println("Conversion du fichier en bytes réussie, longueur : " + fileBytes.length);
                        evenement.setResourceFile(fileBytes);
                        System.out.println("Mise à jour de resourceFile dans l'événement");
                        Evenement updatedEvent = evenementService.updateEvent(id, evenement);
                        System.out.println("Événement mis à jour avec succès : " + updatedEvent.getId());
                        return ResponseEntity.ok("Fichier uploadé avec succès");
                    } catch (IOException e) {
                        System.out.println("Erreur lors de la conversion du fichier en bytes : " + e.getMessage());
                        e.printStackTrace();
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Erreur lors de l'upload : " + e.getMessage());
                    }
                })
                .orElseGet(() -> {
                    System.out.println("Événement non trouvé pour l'ID : " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Événement non trouvé");
                });
    }






    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadResourceFile(@PathVariable Long id) {
        System.out.println("Début de downloadResourceFile pour l'ID : " + id);
        return evenementService.getEventById(id)
                .map(evenement -> {
                    System.out.println("Événement trouvé pour l'ID " + id + " : " + evenement.getTitle());
                    if (evenement.getResourceFile() == null || evenement.getResourceFile().length == 0) {
                        System.out.println("Erreur : Aucun fichier associé à l'événement ID " + id);
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new byte[0]); // Retourne un tableau vide au lieu de null
                    }
                    System.out.println("Fichier trouvé, taille : " + evenement.getResourceFile().length + " bytes");
                    String fileName = evenement.getResourceFileName() != null ? evenement.getResourceFileName() : "resource.pdf";
                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_PDF)
                            .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                            .body(evenement.getResourceFile());
                })
                .orElseGet(() -> {
                    System.out.println("Événement non trouvé pour l'ID : " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new byte[0]); // Retourne un tableau vide au lieu de null
                });
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<String> handleMissingServletRequestPart(MissingServletRequestPartException ex) {
        System.out.println("Erreur : Partie requise manquante - " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erreur : La partie '" + ex.getRequestPartName() + "' est requise mais absente");
    }




}