package telecom_suport.domain;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

public class SolicitareAuditListener {

    @PrePersist
    public void inainteDeCreare(Solicitare solicitare) {
        System.out.println(
                "JPA AUDIT - Solicitare creata: "
                        + solicitare.getNumar()
                        + " la " + LocalDateTime.now()
        );
    }

    @PreUpdate
    public void inainteDeActualizare(Solicitare solicitare) {
        System.out.println(
                "JPA AUDIT - Solicitare actualizata: "
                        + solicitare.getNumar()
                        + " la " + LocalDateTime.now()
        );
    }
}