package tn0esprit.gestionbibliothequespringmicroservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn0esprit.gestionbibliothequespringmicroservice.Entity.StockEntity;

@Repository
public interface StockRepository extends JpaRepository<StockEntity,Long> {
}
