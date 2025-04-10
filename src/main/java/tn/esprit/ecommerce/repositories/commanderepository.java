package tn.esprit.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ecommerce.entities.commande;

public interface commanderepository extends JpaRepository<commande, Long> {
    // You can add custom query methods here if needed
}