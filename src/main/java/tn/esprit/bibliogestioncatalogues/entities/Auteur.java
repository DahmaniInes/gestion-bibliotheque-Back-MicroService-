package tn.esprit.bibliogestioncatalogues.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomComplet;
    private String biographie;
    private String nationalite;

    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @Temporal(TemporalType.DATE)
    private Date dateDeces;

    @ManyToMany(mappedBy = "auteurs")
    private List<Livre> livres;
}
