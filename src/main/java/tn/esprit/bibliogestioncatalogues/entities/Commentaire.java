package tn.esprit.bibliogestioncatalogues.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "livre_id")
    private Livre livre;


    private int note; // De 1 à 5
    private String commentaire;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
}
