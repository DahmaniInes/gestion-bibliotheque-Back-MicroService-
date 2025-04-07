package com.example.livraison.services;


import com.example.livraison.dto.CommandeDTO;
import com.example.livraison.entities.EtatLivraison;
import com.example.livraison.entities.Livraison;
import com.example.livraison.entities.Livreur;
import com.example.livraison.repositories.EtatLivraisonRepository;
import com.example.livraison.repositories.LivraisonRepository;
import com.example.livraison.repositories.LivreurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.example.livraison.feign.CommandeClient;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class LivraisonServiceImpl implements ILivraisonService {

    @Autowired
     LivraisonRepository livraisonRepository;
    @Autowired
    LivreurRepository livreurRepository;
    @Autowired
    EtatLivraisonRepository etatLivraisonRepository;
    @Autowired

    private CommandeClient commandeClient;

    public Livraison creerLivraisonDepuisCommande(Long commandeId) {
        CommandeDTO commande = commandeClient.getCommandeById(commandeId);

        Livraison livraison = new Livraison();
        livraison.setCommandeId(commande.getId());
        livraison.setAdresseLivraison(commande.getAdress());
        livraison.setTelephoneClient(commande.getPhone());
        livraison.setPrixTotal(commande.getPrixTotal());
        livraison.setDateLivraison(new Date());

        // etatLivraison et livreur à définir

        return livraisonRepository.save(livraison);
    }
    @Autowired
    public LivraisonServiceImpl(LivraisonRepository livraisonRepository) {
        this.livraisonRepository = livraisonRepository;
    }

    @Override
    public List<Livraison> getAllLivraisons() {
        return livraisonRepository.findAll();
    }

    @Override
    public Optional<Livraison> getLivraisonById(Long id) {
        return livraisonRepository.findById(id);
    }

    @Override
    public Livraison saveLivraison(Livraison livraison) {
        Livreur livreur = livreurRepository.findById(livraison.getLivreur().getId())
                .orElseThrow(() -> new RuntimeException("Livreur non trouvé"));

        EtatLivraison etat = etatLivraisonRepository.findById(livraison.getEtatLivraison().getId())
                .orElseThrow(() -> new RuntimeException("État de livraison non trouvé"));

        livraison.setLivreur(livreur);
        livraison.setEtatLivraison(etat);
        return livraisonRepository.save(livraison);
    }

    @Override
    public Livraison updateLivraison(Long id, Livraison updatedLivraison) {
        return livraisonRepository.findById(id)
                .map(livraison -> {
                    livraison.setDateLivraison(updatedLivraison.getDateLivraison());
                    livraison.setLivreur(updatedLivraison.getLivreur());
                    livraison.setEtatLivraison(updatedLivraison.getEtatLivraison());
                    return livraisonRepository.save(livraison);
                })
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée avec l'id " + id));
    }

    @Override
    public void deleteLivraison(Long id) {
        livraisonRepository.deleteById(id);
    }
}
