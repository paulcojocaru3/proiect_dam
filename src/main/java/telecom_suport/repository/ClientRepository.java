package telecom_suport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import telecom_suport.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}