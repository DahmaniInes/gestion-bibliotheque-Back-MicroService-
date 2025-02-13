package tn.esprit.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ecommerce.entities.panier;

public interface panierrepository extends JpaRepository<panier, Long> {
}