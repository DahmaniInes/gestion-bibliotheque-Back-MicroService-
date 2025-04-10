package tn0esprit.gestionbibliothequespringmicroservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn0esprit.gestionbibliothequespringmicroservice.Entity.Livre;

public interface LivreRepository extends JpaRepository<Livre, Long> {
}
