package telecom_suport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telecom_suport.domain.IstoricSolicitare;

public interface IstoricSolicitareRepository extends JpaRepository<IstoricSolicitare, Long> {
}