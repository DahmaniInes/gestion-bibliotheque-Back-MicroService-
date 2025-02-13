package tn.esprit.bibliogestioncatalogues.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.bibliogestioncatalogues.entities.Auteur;
import tn.esprit.bibliogestioncatalogues.repo.AuteurRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuteurService implements IAuteurService{
    @Autowired
    private AuteurRepository auteurRepository;

    public List<Auteur> getAllAuteurs() {
        return auteurRepository.findAll();
    }

    public Optional<Auteur> getAuteurById(Long id) {
        return auteurRepository.findById(id);
    }

    public Auteur saveAuteur(Auteur auteur) {
        return auteurRepository.save(auteur);
    }

    public Auteur updateAuteur(Long id, Auteur updatedAuteur) {
        return auteurRepository.findById(id).map(auteur -> {
            auteur.setNomComplet(updatedAuteur.getNomComplet());
            auteur.setBiographie(updatedAuteur.getBiographie());
            auteur.setNationalite(updatedAuteur.getNationalite());
            auteur.setDateNaissance(updatedAuteur.getDateNaissance());
            auteur.setDateDeces(updatedAuteur.getDateDeces());
            return auteurRepository.save(auteur);
        }).orElse(null);
    }

    public void deleteAuteur(Long id) {
        auteurRepository.deleteById(id);
    }
}
