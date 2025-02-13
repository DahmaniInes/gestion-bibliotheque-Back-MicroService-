package tn.esprit.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ecommerce.entities.commande;

public interface commanderepository extends JpaRepository<commande, Long> {
}