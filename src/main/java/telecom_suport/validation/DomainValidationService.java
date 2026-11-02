package telecom_suport.validation;

import telecom_suport.entity.*;
import java.time.LocalDateTime;
public interface DomainValidationService {
 void valideazaResponsabil(Utilizator responsabil);
 void valideazaSolutionarea(Solicitare solicitare, String solutie);
 void valideazaTermen(LocalDateTime termen);
 void valideazaTranzitie(StareSolicitare veche, StareSolicitare noua);
}
