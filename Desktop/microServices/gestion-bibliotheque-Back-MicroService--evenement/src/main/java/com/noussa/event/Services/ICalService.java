package com.noussa.event.Services;

import com.noussa.event.Entities.Evenement;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class ICalService {

    public String generateICalContent(Evenement evenement) {
        // Créer un calendrier iCal
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//NousSA//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        // Convertir les LocalDateTime en ZonedDateTime puis en DateTime de iCal4j
        ZonedDateTime startZoned = evenement.getEventDate().atZone(ZoneId.systemDefault());
        ZonedDateTime endZoned = evenement.getEndDate().atZone(ZoneId.systemDefault());

        DateTime start = new DateTime(startZoned.toInstant().toEpochMilli());
        DateTime end = new DateTime(endZoned.toInstant().toEpochMilli());

        // Créer un événement avec la nouvelle API
        VEvent event = new VEvent();
        event.getProperties().add(new DtStart(start));
        event.getProperties().add(new DtEnd(end));
        event.getProperties().add(new Summary(evenement.getTitle()));

        // Générer un UID unique avec le nouveau générateur
        UidGenerator ug = new RandomUidGenerator();
        event.getProperties().add(ug.generateUid());

        // Ajouter la description
        if (evenement.getDescription() != null) {
            event.getProperties().add(new Description(evenement.getDescription()));
        }

        // Ajouter le lieu
        if (evenement.getLocation() != null) {
            event.getProperties().add(new Location(evenement.getLocation()));
        }

        // Ajouter le lien Zoom si présent
        if (evenement.getResourceLink() != null && !evenement.getResourceLink().isEmpty()) {
            try {
                event.getProperties().add(new Url(URI.create(evenement.getResourceLink())));
            } catch (Exception e) {
                System.err.println("Invalid URL: " + evenement.getResourceLink());
            }
        }

        // Ajouter l'événement au calendrier
        calendar.getComponents().add(event);

        return calendar.toString();
    }
}