package tn0esprit.gestionbibliothequespringmicroservice.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String isbn;
    private int anneePublication;
    private String editeur;
    private int nombrePages;
    private String resume;
    private double prix;
    private int stockDisponible;
    private String imageCouverture;




}
