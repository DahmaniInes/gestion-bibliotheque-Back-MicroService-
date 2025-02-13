package tn.esprit.bibliogestioncatalogues.services;

import tn.esprit.bibliogestioncatalogues.entities.Categorie;

import java.util.List;
import java.util.Optional;

public interface ICategorieService {
    List<Categorie> getAllCategories();
    Optional<Categorie> getCategorieById(Long id);
    Categorie saveCategorie(Categorie categorie);
    Categorie updateCategorie(Long id, Categorie categorie);
    void deleteCategorie(Long id);
}
