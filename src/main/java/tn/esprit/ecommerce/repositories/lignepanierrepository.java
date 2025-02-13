package tn.esprit.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.ecommerce.entities.lignepanier;

public interface lignepanierrepository extends JpaRepository<lignepanier, Long> {
}