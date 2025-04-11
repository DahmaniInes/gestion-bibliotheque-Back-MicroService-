package com.noussa.event.Services;

import com.noussa.event.Entities.Evenement;
import com.noussa.event.Repositories.EvenementRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvenementService {
    @Autowired
    private EvenementRepository evenementRepository;

    // 🔹 Créer un événement
    public Evenement createEvent(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    // 🔹 Lire tous les événements
    public List<Evenement> getAllEvents() {
        return evenementRepository.findAll();
    }

    // 🔹 Lire un événement par ID
    public Optional<Evenement> getEventById(Long id) {
        return evenementRepository.findById(id);
    }

    // 🔹 Mettre à jour un événement
    public Evenement updateEvent(Long id, Evenement updatedEvent) {
        Optional<Evenement> existingEventOpt = evenementRepository.findById(id);

        if (existingEventOpt.isPresent()) {
            Evenement existingEvent = existingEventOpt.get();
            existingEvent.setTitle(updatedEvent.getTitle());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setEventDate(updatedEvent.getEventDate());
            existingEvent.setEndDate(updatedEvent.getEndDate());
            existingEvent.setLocation(updatedEvent.getLocation());
            existingEvent.setMaxParticipants(updatedEvent.getMaxParticipants());
            existingEvent.setStatus(updatedEvent.getStatus());
            existingEvent.setResourceLink(updatedEvent.getResourceLink()); // Mise à jour du lien
            existingEvent.setResourceFile(updatedEvent.getResourceFile()); // Mise à jour du fichier
            //existingEvent.getResourceFileName(updatedEvent.getResourceFileName()); // Mise à jour du fichier

            return evenementRepository.save(existingEvent);
        } else {
            return null;
        }
    }

    // 🔹 Supprimer un événement
    public void deleteEvent(Long id) {
        evenementRepository.deleteById(id);
    }
}
