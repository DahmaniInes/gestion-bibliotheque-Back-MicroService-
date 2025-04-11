package tn.esprit.bibliogestioncatalogues.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.bibliogestioncatalogues.entities.Categorie;
import tn.esprit.bibliogestioncatalogues.entities.Livre;
import tn.esprit.bibliogestioncatalogues.repo.CategorieRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService implements ICategorieService{
    @Autowired
    private CategorieRepository categorieRepository;

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Optional<Categorie> getCategorieById(Long id) {
        return categorieRepository.findById(id);
    }

    public Categorie saveCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Categorie updateCategorie(Long id, Categorie updatedCategorie) {
        return categorieRepository.findById(id).map(categorie -> {
            categorie.setNom(updatedCategorie.getNom());
            categorie.setDescription(updatedCategorie.getDescription());
            return categorieRepository.save(categorie);
        }).orElse(null);
    }

    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }

    @Override
    public List<Livre> getLivresByCategorie(Long categorieId) {
        Optional<Categorie> categorie = categorieRepository.findById(categorieId);
        return categorie.map(Categorie::getLivres).orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
    }
}
