package tn.esprit.BlogMs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.BlogMs.entity.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
