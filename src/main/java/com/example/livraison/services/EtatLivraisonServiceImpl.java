package com.example.livraison.services;


import com.example.livraison.entities.EtatLivraison;
import com.example.livraison.repositories.EtatLivraisonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtatLivraisonServiceImpl implements IEtatLivraisonService {

    private final EtatLivraisonRepository etatLivraisonRepository;

    @Autowired
    public EtatLivraisonServiceImpl(EtatLivraisonRepository etatLivraisonRepository) {
        this.etatLivraisonRepository = etatLivraisonRepository;
    }

    @Override
    public List<EtatLivraison> getAllEtatLivraisons() {
        return etatLivraisonRepository.findAll();
    }

    @Override
    public Optional<EtatLivraison> getEtatLivraisonById(Long id) {
        return etatLivraisonRepository.findById(id);
    }

    @Override
    public EtatLivraison saveEtatLivraison(EtatLivraison etatLivraison) {
        return etatLivraisonRepository.save(etatLivraison);
    }

    @Override
    public EtatLivraison updateEtatLivraison(Long id, EtatLivraison updatedEtat) {
        return etatLivraisonRepository.findById(id)
                .map(etat -> {
                    etat.setLibelle(updatedEtat.getLibelle());
                    return etatLivraisonRepository.save(etat);
                })
                .orElseThrow(() -> new RuntimeException("Etat de livraison non trouvé avec l'id " + id));
    }

    @Override
    public void deleteEtatLivraison(Long id) {
        etatLivraisonRepository.deleteById(id);
    }
}
