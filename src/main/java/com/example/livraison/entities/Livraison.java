package com.example.livraison.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "livraison")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateLivraison;
    private Long commandeId; // vient de commande.id

    private String adresseLivraison; // vient de commande.adress

    private String telephoneClient; // optionnel - vient de commande.phone
    private String methodePaiement;
    private Double prixTotal;




    @ManyToOne
    @JoinColumn(name = "livreur_id", nullable = false)
    private Livreur livreur;

    @ManyToOne
    @JoinColumn(name = "etat_id", nullable = false)
    private EtatLivraison etatLivraison;
}
