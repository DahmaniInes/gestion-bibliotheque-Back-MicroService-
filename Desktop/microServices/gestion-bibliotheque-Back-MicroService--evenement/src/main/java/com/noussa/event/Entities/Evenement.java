package com.noussa.event.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Evenement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("resourceLink")
    private String resourceLink; // Ex. : lien Zoom
    @Lob // Pour stocker un fichier volumineux (PDF)
    @JsonProperty("resourceFile")
    private byte[] resourceFile; // Fichier binaire (PDF)

    @JsonProperty("resourceFileName") // Nouveau champ pour le nom du fichier
    private String resourceFileName;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("eventDate")
    private LocalDateTime eventDate;

    @JsonProperty("endDate")
    private LocalDateTime endDate;

    @JsonProperty("location")
    private String location;

    @JsonProperty("maxParticipants")
    private Integer maxParticipants;

    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private EventStatus status;

    // Getters et Setters manuels
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getEventDate() { return eventDate; }
    public void setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Integer getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }


    public String getResourceLink() { return resourceLink; }
    public void setResourceLink(String resourceLink) { this.resourceLink = resourceLink; }
    public byte[] getResourceFile() { return resourceFile; }
    public void setResourceFile(byte[] resourceFile) { this.resourceFile = resourceFile; }


    public String getResourceFileName() { return resourceFileName; } // Nouveau getter
    public void setResourceFileName(String resourceFileName) { this.resourceFileName = resourceFileName; } // Nouveau setter



}