package com.example.livraison.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "livreur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livreur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String prenom;

    // Téléphone, email, etc.

    private String email;
    private String telephone;
    private String vehicule;
    private Boolean disponible;
    @OneToMany(mappedBy = "livreur")
    @JsonManagedReference
    private List<Livraison> livraisons;
}
