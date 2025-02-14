package com.example.livraison.services;


import com.example.livraison.entities.Livreur;
import com.example.livraison.repositories.LivreurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivreurServiceImpl implements ILivreurService {

    private final LivreurRepository livreurRepository;

    @Autowired
    public LivreurServiceImpl(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
    }

    @Override
    public List<Livreur> getAllLivreurs() {
        return livreurRepository.findAll();
    }

    @Override
    public Optional<Livreur> getLivreurById(Long id) {
        return livreurRepository.findById(id);
    }

    @Override
    public Livreur saveLivreur(Livreur livreur) {
        return livreurRepository.save(livreur);
    }

    @Override
    public Livreur updateLivreur(Long id, Livreur updatedLivreur) {
        return livreurRepository.findById(id)
                .map(livreur -> {
                    livreur.setNom(updatedLivreur.getNom());
                    livreur.setPrenom(updatedLivreur.getPrenom());
                    livreur.setEmail(updatedLivreur.getEmail());
                    livreur.setTelephone(updatedLivreur.getTelephone());
                    livreur.setVehicule(updatedLivreur.getVehicule());
                    livreur.setDisponible(updatedLivreur.getDisponible());
                    return livreurRepository.save(livreur);
                })
                .orElseThrow(() -> new RuntimeException("Livreur non trouvé avec l'id " + id));
    }

    @Override
    public void deleteLivreur(Long id) {
        livreurRepository.deleteById(id);
    }
}
