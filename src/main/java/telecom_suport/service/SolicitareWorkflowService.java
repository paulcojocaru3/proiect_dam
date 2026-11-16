package telecom_suport.service;
import telecom_suport.domain.*;
import java.time.LocalDateTime;
public interface SolicitareWorkflowService {
 Solicitare inregistreazaSolicitare(Client client, String descriere, Categorie categorie, Canal canal, Prioritate prioritate);
 void atribuieSolicitare(Solicitare solicitare, Utilizator responsabil, LocalDateTime termen, Utilizator autor);
 void actualizeazaStare(Solicitare solicitare, StareSolicitare stare, String observatii, Utilizator autor);
 void solutioneaza(Solicitare solicitare, String solutie, Utilizator autor);
}
