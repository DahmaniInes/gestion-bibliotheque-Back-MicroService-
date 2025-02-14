package com.example.livraison.services;


import com.example.livraison.entities.Livreur;
import java.util.List;
import java.util.Optional;

public interface ILivreurService {

    List<Livreur> getAllLivreurs();

    Optional<Livreur> getLivreurById(Long id);

    Livreur saveLivreur(Livreur livreur);

    Livreur updateLivreur(Long id, Livreur updatedLivreur);

    void deleteLivreur(Long id);
}
