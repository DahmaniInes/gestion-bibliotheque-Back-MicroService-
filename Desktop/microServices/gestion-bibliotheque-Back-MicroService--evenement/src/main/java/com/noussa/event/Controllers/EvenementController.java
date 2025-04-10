package com.noussa.event.Controllers;


import com.noussa.event.Entities.Evenement;
import com.noussa.event.Services.EvenementService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor

@RestController
@RequestMapping("/event")
public class EvenementController {
    @Autowired
    private EvenementService evenementService;

    // 🔹 Créer un événement




    @PostMapping

    public ResponseEntity<?> createEvent(@RequestBody(required = true) Evenement evenement) {
        try {
            System.out.println("Received event: " + evenement); // Pour le debugging
            Evenement savedEvent = evenementService.createEvent(evenement);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
        } catch (Exception e) {
            e.printStackTrace(); // Pour voir la stack trace complète
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de la création de l'événement : " + e.getMessage());
        }
    }

    // 🔹 Lire tous les événements
    @GetMapping
    public List<Evenement> getAllEvents() {
        return evenementService.getAllEvents();
    }

    // 🔹 Lire un événement par ID
    @GetMapping("/{id}")
    public ResponseEntity<Evenement> getEventById(@PathVariable Long id) {
        Optional<Evenement> event = evenementService.getEventById(id);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 🔹 Mettre à jour un événement
    @PutMapping("/{id}")
    public ResponseEntity<Evenement> updateEvent(@PathVariable Long id, @RequestBody Evenement updatedEvent) {
        return ResponseEntity.ok(evenementService.updateEvent(id, updatedEvent));
    }

    // 🔹 Supprimer un événement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        evenementService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
