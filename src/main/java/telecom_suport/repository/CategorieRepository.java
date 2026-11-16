package telecom_suport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telecom_suport.domain.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}