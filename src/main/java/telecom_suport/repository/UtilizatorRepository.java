package telecom_suport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telecom_suport.domain.Utilizator;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Long> {
}