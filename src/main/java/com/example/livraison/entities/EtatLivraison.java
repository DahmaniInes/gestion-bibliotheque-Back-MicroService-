package com.example.livraison.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "etat_livraison")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtatLivraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Exemple de libellé: "en cours", "livré", "annulé"
    private String libelle;
}
