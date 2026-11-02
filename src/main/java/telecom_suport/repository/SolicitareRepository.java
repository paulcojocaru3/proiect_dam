package telecom_suport.repository;
import telecom_suport.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface SolicitareRepository extends JpaRepository<Solicitare,Long> {
 boolean existsByNumar(String numar);
 List<Solicitare> findByClientId(Long clientId);
 List<Solicitare> findByResponsabilIdAndStareIn(Long userId, Collection<StareSolicitare> stari);
}
