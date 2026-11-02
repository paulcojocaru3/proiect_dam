package telecom_suport.service;

import telecom_suport.entity.*;
import java.time.LocalDateTime;
public interface SolicitareWorkflowService {
 Solicitare inregistreazaSolicitare(Long clientId,String descriere,Long categorieId,Canal canal,Prioritate prioritate);
 Solicitare atribuieSolicitare(Long id,Long responsabilId,LocalDateTime termen,Long autorId);
 Solicitare actualizeazaStare(Long id,StareSolicitare stare,String observatii,Long autorId);
 Solicitare solutioneaza(Long id,String solutie,Long autorId);
}
