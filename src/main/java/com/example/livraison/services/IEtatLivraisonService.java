package com.example.livraison.services;


import com.example.livraison.entities.EtatLivraison;
import java.util.List;
import java.util.Optional;

public interface IEtatLivraisonService {

    List<EtatLivraison> getAllEtatLivraisons();

    Optional<EtatLivraison> getEtatLivraisonById(Long id);

    EtatLivraison saveEtatLivraison(EtatLivraison etatLivraison);

    EtatLivraison updateEtatLivraison(Long id, EtatLivraison updatedEtat);

    void deleteEtatLivraison(Long id);
}
