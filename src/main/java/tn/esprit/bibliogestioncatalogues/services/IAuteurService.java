package tn.esprit.bibliogestioncatalogues.services;

import tn.esprit.bibliogestioncatalogues.entities.Auteur;

import java.util.List;
import java.util.Optional;

public interface IAuteurService {
    List<Auteur> getAllAuteurs();
    Optional<Auteur> getAuteurById(Long id);
    Auteur saveAuteur(Auteur auteur);
    Auteur updateAuteur(Long id, Auteur auteur);
    void deleteAuteur(Long id);
}
