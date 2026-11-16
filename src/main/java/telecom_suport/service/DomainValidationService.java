package telecom_suport.service;

import telecom_suport.domain.*;
import java.time.LocalDateTime;
public interface DomainValidationService {
 void valideazaResponsabil(Utilizator responsabil);
 void valideazaSolutionarea(Solicitare solicitare, String solutie);
 void valideazaTermen(LocalDateTime termen);
 void valideazaTranzitie(StareSolicitare veche, StareSolicitare noua);
}
