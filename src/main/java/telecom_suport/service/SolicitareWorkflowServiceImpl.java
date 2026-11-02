package telecom_suport.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telecom_suport.entity.*;
import telecom_suport.repository.*;
import telecom_suport.factory.SolicitareFactory;
import telecom_suport.validation.*;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
@Service
@Transactional
public class SolicitareWorkflowServiceImpl implements SolicitareWorkflowService {
 private final SolicitareRepository solicitari;
 private final ClientRepository clienti;
 private final UtilizatorRepository utilizatori;
 private final CategorieRepository categorii;
 private final SolicitareFactory fabrica;
 private final DomainValidationService validare;
 private final AuditService audit;
 public SolicitareWorkflowServiceImpl(SolicitareRepository s,ClientRepository c,UtilizatorRepository u,CategorieRepository cat,SolicitareFactory f,DomainValidationService v,AuditService a) {
  solicitari=s;clienti=c;utilizatori=u;categorii=cat;fabrica=f;validare=v;audit=a;
 }
 public Solicitare inregistreazaSolicitare(Long clientId,String descriere,Long categorieId,Canal canal,Prioritate prioritate) {
  Client client=clienti.findById(clientId).orElseThrow(()->new RegulaBusinessException("Client inexistent"));
  if (categorieId == null) throw new RegulaBusinessException("Categoria este obligatorie");
  Categorie categorie=categorii.findById(categorieId).orElseThrow(()->new RegulaBusinessException("Categorie inexistenta"));
  String numar;
  do { numar="REC-"+String.format("%04d", ThreadLocalRandom.current().nextInt(10000)); } while(solicitari.existsByNumar(numar));
  Solicitare s=fabrica.noua(client,descriere,canal,prioritate,numar);s.setCategorie(categorie);
  return solicitari.save(s);
 }
 private Solicitare gaseste(Long id) {return solicitari.findById(id).orElseThrow(()->new SolicitareNotFoundException(id));}
 private Utilizator utilizator(Long id) {return utilizatori.findById(id).orElseThrow(()->new RegulaBusinessException("Utilizator inexistent"));}
 private void verificaRol(Utilizator u, Rol... roluri) {
  if (u.getRol() == null || java.util.Arrays.stream(roluri).noneMatch(rol -> rol == u.getRol())) throw new RegulaBusinessException("Rolul nu permite aceasta operatie");
 }
 public Solicitare atribuieSolicitare(Long id,Long responsabilId,LocalDateTime termen,Long autorId) {
  Solicitare s=gaseste(id);Utilizator r=utilizator(responsabilId),a=utilizator(autorId);
  verificaRol(a, Rol.COORDONATOR, Rol.ADMINISTRATOR);
  validare.valideazaResponsabil(r);validare.valideazaTermen(termen);validare.valideazaTranzitie(s.getStare(),StareSolicitare.ALOCATA);
  s.atribuie(r,termen,a);audit.inregistreaza(s,a,"ATRIBUIRE");return solicitari.save(s);
 }
 public Solicitare actualizeazaStare(Long id,StareSolicitare stare,String observatii,Long autorId) {
  Solicitare s=gaseste(id);Utilizator a=utilizator(autorId);verificaRol(a, Rol.ANGAJAT_RESPONSABIL, Rol.ADMINISTRATOR);validare.valideazaTranzitie(s.getStare(),stare);
  if(stare==StareSolicitare.SOLUTIONATA) throw new RegulaBusinessException("Folositi metoda solutioneaza");
  if (s.getResponsabil() == null || !s.getResponsabil().getId().equals(a.getId())) throw new RegulaBusinessException("Doar responsabilul poate actualiza solicitarea");
  s.schimbaStarea(stare,observatii,a);audit.inregistreaza(s,a,"SCHIMBARE_STARE");return solicitari.save(s);
 }
 public Solicitare solutioneaza(Long id,String solutie,Long autorId) {
  Solicitare s=gaseste(id);Utilizator a=utilizator(autorId);verificaRol(a, Rol.ANGAJAT_RESPONSABIL, Rol.ADMINISTRATOR);validare.valideazaSolutionarea(s,solutie);
  if (s.getResponsabil() == null || !s.getResponsabil().getId().equals(a.getId())) throw new RegulaBusinessException("Doar responsabilul poate solutiona solicitarea");
  s.solutioneaza(solutie,a);audit.inregistreaza(s,a,"SOLUTIONARE");return solicitari.save(s);
 }
}
