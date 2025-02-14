package com.example.livraison.services;


import com.example.livraison.entities.Livraison;
import java.util.List;
import java.util.Optional;

public interface ILivraisonService {

    List<Livraison> getAllLivraisons();

    Optional<Livraison> getLivraisonById(Long id);

    Livraison saveLivraison(Livraison livraison);

    Livraison updateLivraison(Long id, Livraison updatedLivraison);

    void deleteLivraison(Long id);
}
