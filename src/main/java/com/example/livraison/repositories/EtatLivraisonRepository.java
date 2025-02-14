package com.example.livraison.repositories;



import com.example.livraison.entities.EtatLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatLivraisonRepository extends JpaRepository<EtatLivraison, Long> {
}
