package com.example.livraison.services;


import com.example.livraison.entities.Livraison;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILivraisonService {

    List<Livraison> findLivraisonByDate (LocalDateTime dateLivraison);

    List<Livraison> getAllLivraisons();
    Livraison creerLivraisonDepuisCommande(Long commandeId);

    Optional<Livraison> getLivraisonById(Long id);

    Livraison saveLivraison(Livraison livraison);

    Livraison updateLivraison(Long id, Livraison updatedLivraison);

    void deleteLivraison(Long id);

    Page<Livraison> getAllLivraisonsPaginated(Pageable pageable);

    public byte[] generatePdfForLivraison(Long id);

}
