package telecom_suport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telecom_suport.domain.Solicitare;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import telecom_suport.domain.StareSolicitare;

public interface SolicitareRepository extends JpaRepository<Solicitare, Long> {Solicitare findByNumar(String numar);
    @Query("SELECT s FROM Solicitare s WHERE s.stare = :stare")
    List<Solicitare> findByStare(@Param("stare") StareSolicitare stare);

}