package telecom_suport.validation;

import org.springframework.stereotype.Service;
import telecom_suport.entity.*;
import java.time.LocalDateTime;
@Service
public class DomainValidationServiceImpl implements DomainValidationService {
 public void valideazaResponsabil(Utilizator u) { if(u == null) throw new RegulaBusinessException("Responsabil lipsa"); if(!u.isActiv()) throw new UtilizatorInactivException(); }
 public void valideazaSolutionarea(Solicitare s,String solutie) { if(s == null || s.getStare()!=StareSolicitare.IN_LUCRU || solutie==null || solutie.isBlank()) throw new RegulaBusinessException("Solutionare invalida"); }
 public void valideazaTermen(LocalDateTime t) { if(t==null || !t.isAfter(LocalDateTime.now())) throw new RegulaBusinessException("Termenul trebuie sa fie in viitor"); }
 public void valideazaTranzitie(StareSolicitare v,StareSolicitare n) {
  boolean ok = v!=null && n!=null && switch(v) {
   case NOUA -> n==StareSolicitare.ALOCATA;
   case ALOCATA -> n==StareSolicitare.IN_LUCRU;
   case IN_LUCRU -> n==StareSolicitare.IN_ASTEPTARE || n==StareSolicitare.SOLUTIONATA;
   case IN_ASTEPTARE -> n==StareSolicitare.IN_LUCRU;
   case SOLUTIONATA -> n==StareSolicitare.INCHISA;
   default -> false;
  };
  if(!ok) throw new RegulaBusinessException("Tranzitie nepermisa");
 }
}
