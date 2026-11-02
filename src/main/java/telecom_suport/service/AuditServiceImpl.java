package telecom_suport.service;

import org.springframework.stereotype.Service;
import telecom_suport.entity.*;
import java.time.LocalDateTime;
@Service
public class AuditServiceImpl implements AuditService {
 public void inregistreaza(Solicitare s,Utilizator autor,String operatie) {
  if(s==null || autor==null || operatie==null) throw new IllegalArgumentException("Date audit incomplete");
  // Evenimentele care schimba starea sunt pastrate in istoricul agregatului.
  System.out.println("AUDIT " + LocalDateTime.now() + " solicitare=" + s.getNumar() + " autor=" + autor.getEmail() + " operatie=" + operatie);
 }
}
