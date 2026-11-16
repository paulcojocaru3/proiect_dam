package telecom_suport.service;
import org.springframework.transaction.annotation.Transactional;
import telecom_suport.repository.SolicitareRepository;
import org.springframework.stereotype.Service;
import telecom_suport.domain.*;
import telecom_suport.factory.SolicitareFactory;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class SolicitareWorkflowServiceImpl implements SolicitareWorkflowService  {
 private final SolicitareFactory fabrica;
 private final DomainValidationService validare;
 private final AuditService audit;
 private final SolicitareRepository solicitareRepository;
 public SolicitareWorkflowServiceImpl(
         SolicitareFactory fabrica,
         DomainValidationService validare,
         AuditService audit,
         SolicitareRepository solicitareRepository) {

  this.fabrica = fabrica;
  this.validare = validare;
  this.audit = audit;
  this.solicitareRepository = solicitareRepository;
 }

 public Solicitare inregistreazaSolicitare(Client client,String descriere,Categorie categorie,Canal canal,Prioritate prioritate) {
  String numar="REC-"+UUID.randomUUID().toString().replace("-","").substring(0,12).toUpperCase();
  Solicitare solicitare=fabrica.noua(client,descriere,canal,prioritate,numar);
  solicitare.setCategorie(categorie);
  return solicitareRepository.save(solicitare);
 }
 public void atribuieSolicitare(Solicitare s,Utilizator responsabil,LocalDateTime termen,Utilizator autor) {
  if(s==null || autor==null) throw new RegulaBusinessException("Date obligatorii lipsa");
  validare.valideazaResponsabil(responsabil); validare.valideazaTermen(termen);
  validare.valideazaTranzitie(s.getStare(),StareSolicitare.ALOCATA);
  s.atribuie(responsabil,termen,autor); audit.inregistreaza(s,autor,"ATRIBUIRE");
  solicitareRepository.save(s);
 }
 public void actualizeazaStare(Solicitare s,StareSolicitare stare,String observatii,Utilizator autor) {
  if(s==null || autor==null) throw new RegulaBusinessException("Date obligatorii lipsa");
  validare.valideazaTranzitie(s.getStare(),stare);
  if(stare==StareSolicitare.SOLUTIONATA) throw new RegulaBusinessException("Folositi metoda solutioneaza");
  s.schimbaStarea(stare,observatii,autor); audit.inregistreaza(s,autor,"SCHIMBARE_STARE");
  solicitareRepository.save(s);
 }
 public void solutioneaza(Solicitare s,String solutie,Utilizator autor) {
  if(s==null || autor==null) throw new RegulaBusinessException("Date obligatorii lipsa");
  validare.valideazaSolutionarea(s,solutie);
  s.solutioneaza(solutie,autor); audit.inregistreaza(s,autor,"SOLUTIONARE");
  solicitareRepository.save(s);
 }
}
