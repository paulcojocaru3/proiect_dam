package telecom_suport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import telecom_suport.entity.*;
import telecom_suport.validation.*;
import telecom_suport.computation.*;
import telecom_suport.service.*;
import telecom_suport.repository.*;
import telecom_suport.factory.*;
import java.time.*;
import java.util.*;

class DomainServicesTest {
 @Test void validareRespingeUtilizatorInactiv() {
  Utilizator u=new Utilizator();u.setActiv(false);
  assertThrows(UtilizatorInactivException.class,()->new DomainValidationServiceImpl().valideazaResponsabil(u));
 }
 @Test void validareRespingeSolutieGoala() {
  Solicitare s=new Solicitare();
  assertThrows(RegulaBusinessException.class,()->new DomainValidationServiceImpl().valideazaSolutionarea(s," "));
 }
 @Test void calculDurata() {
  SolicitareRepository repo=mock(SolicitareRepository.class);
  SolicitareComputationServiceImpl calc=new SolicitareComputationServiceImpl(repo);
  assertTrue(calc.durataSolutionarii(new Solicitare()).isEmpty());
  assertTrue(calc.timpMediu(new telecom_suport.valueobject.PerioadaRaportare(LocalDate.now().minusDays(1),LocalDate.now()),null).isEmpty());
 }
 @Test void workflowInregistreazaSolicitare() {
  SolicitareRepository sr=mock(SolicitareRepository.class);ClientRepository cr=mock(ClientRepository.class);
  UtilizatorRepository ur=mock(UtilizatorRepository.class);CategorieRepository cat=mock(CategorieRepository.class);
  when(cr.findById(1L)).thenReturn(Optional.of(new Client()));
  when(cat.findById(1L)).thenReturn(Optional.of(new Categorie()));
  when(sr.save(any(Solicitare.class))).thenAnswer(inv->inv.getArgument(0));
  var svc=new SolicitareWorkflowServiceImpl(sr,cr,ur,cat,new SolicitareFactory(),new DomainValidationServiceImpl(),mock(AuditService.class));
  Solicitare s=svc.inregistreazaSolicitare(1L,"Internet indisponibil",1L,Canal.TELEFON,Prioritate.MEDIE);
  assertEquals(StareSolicitare.NOUA,s.getStare());assertNotNull(s.getNumar());assertNotNull(s.getDataCrearii());
 }
 @Test void inregistrareaFaraCategorieEsteRespinsa() {
  SolicitareRepository sr=mock(SolicitareRepository.class);ClientRepository cr=mock(ClientRepository.class);
  when(cr.findById(1L)).thenReturn(Optional.of(new Client()));
  var svc=new SolicitareWorkflowServiceImpl(sr,cr,mock(UtilizatorRepository.class),mock(CategorieRepository.class),new SolicitareFactory(),new DomainValidationServiceImpl(),mock(AuditService.class));
  assertThrows(RegulaBusinessException.class,()->svc.inregistreazaSolicitare(1L,"Descriere",null,Canal.TELEFON,Prioritate.MEDIE));
  verify(sr,never()).save(any());
 }
 @Test void factoryRespingeCampuriObligatoriiSiNumarInvalid() {
  SolicitareFactory factory=new SolicitareFactory(); Client client=new Client();
  assertThrows(IllegalArgumentException.class,()->factory.noua(null,"descriere",Canal.EMAIL,Prioritate.MEDIE,"REC-0001"));
  assertThrows(IllegalArgumentException.class,()->factory.noua(client," ",Canal.EMAIL,Prioritate.MEDIE,"REC-0001"));
  assertThrows(IllegalArgumentException.class,()->factory.noua(client,"descriere",Canal.EMAIL,Prioritate.MEDIE,"REC-123456"));
 }
 @Test void validareRespingeTermenTrecutSiTranzitiiNepermise() {
  DomainValidationService validation=new DomainValidationServiceImpl();
  assertThrows(RegulaBusinessException.class,()->validation.valideazaTermen(LocalDateTime.now().minusMinutes(1)));
  assertThrows(RegulaBusinessException.class,()->validation.valideazaTranzitie(StareSolicitare.NOUA,StareSolicitare.IN_LUCRU));
 }
 @Test void workflowRespingeResponsabilInactivSiAutorFaraRol() {
  SolicitareRepository sr=mock(SolicitareRepository.class);UtilizatorRepository ur=mock(UtilizatorRepository.class);
  Solicitare s=new Solicitare();Utilizator coord=new Utilizator();coord.setRol(Rol.COORDONATOR);
  Utilizator inactiv=new Utilizator();inactiv.setActiv(false);
  try { var field=Utilizator.class.getDeclaredField("id");field.setAccessible(true);field.set(coord,1L);field.set(inactiv,2L); } catch(ReflectiveOperationException e) { throw new RuntimeException(e); }
  when(sr.findById(1L)).thenReturn(Optional.of(s));when(ur.findById(1L)).thenReturn(Optional.of(coord));when(ur.findById(2L)).thenReturn(Optional.of(inactiv));
  var svc=new SolicitareWorkflowServiceImpl(sr,mock(ClientRepository.class),ur,mock(CategorieRepository.class),new SolicitareFactory(),new DomainValidationServiceImpl(),mock(AuditService.class));
  assertThrows(UtilizatorInactivException.class,()->svc.atribuieSolicitare(1L,2L,LocalDateTime.now().plusDays(1),1L));
  assertThrows(RegulaBusinessException.class,()->svc.atribuieSolicitare(1L,1L,LocalDateTime.now().plusDays(1),2L));
  verify(sr,never()).save(any());
 }
 @Test void workflowRespingeActualizareDeAltAngajatSiSolutionareFaraDescriere() {
  SolicitareRepository sr=mock(SolicitareRepository.class);UtilizatorRepository ur=mock(UtilizatorRepository.class);
  Utilizator responsabil=new Utilizator(),altul=new Utilizator();responsabil.setRol(Rol.ANGAJAT_RESPONSABIL);altul.setRol(Rol.ANGAJAT_RESPONSABIL);
  try { var field=Utilizator.class.getDeclaredField("id");field.setAccessible(true);field.set(responsabil,1L);field.set(altul,2L); } catch(ReflectiveOperationException e) { throw new RuntimeException(e); }
  Solicitare s=new Solicitare();s.atribuie(responsabil,LocalDateTime.now().plusDays(1),responsabil);
  when(sr.findById(1L)).thenReturn(Optional.of(s));when(ur.findById(1L)).thenReturn(Optional.of(responsabil));when(ur.findById(2L)).thenReturn(Optional.of(altul));
  var svc=new SolicitareWorkflowServiceImpl(sr,mock(ClientRepository.class),ur,mock(CategorieRepository.class),new SolicitareFactory(),new DomainValidationServiceImpl(),mock(AuditService.class));
  assertThrows(RegulaBusinessException.class,()->svc.actualizeazaStare(1L,StareSolicitare.IN_LUCRU,"",2L));
  svc.actualizeazaStare(1L,StareSolicitare.IN_LUCRU,"Observatie",1L);
  assertThrows(RegulaBusinessException.class,()->svc.solutioneaza(1L," ",1L));
  verify(sr,times(1)).save(any());
 }
 @Test void workflowAtribuireSiIstoric() {
  SolicitareRepository sr=mock(SolicitareRepository.class);ClientRepository cr=mock(ClientRepository.class);
  UtilizatorRepository ur=mock(UtilizatorRepository.class);CategorieRepository cat=mock(CategorieRepository.class);
  Solicitare s=new Solicitare();Utilizator u=new Utilizator();u.setNume("Angajat");u.setRol(Rol.ANGAJAT_RESPONSABIL);
  Utilizator coordonator=new Utilizator();coordonator.setRol(Rol.COORDONATOR);
  try { var field=Utilizator.class.getDeclaredField("id"); field.setAccessible(true); field.set(u,2L); } catch (ReflectiveOperationException e) { throw new RuntimeException(e); }
  try { var field=Utilizator.class.getDeclaredField("id"); field.setAccessible(true); field.set(coordonator,3L); } catch (ReflectiveOperationException e) { throw new RuntimeException(e); }
  when(sr.findById(1L)).thenReturn(Optional.of(s));when(ur.findById(2L)).thenReturn(Optional.of(u));when(ur.findById(3L)).thenReturn(Optional.of(coordonator));
  when(sr.save(any(Solicitare.class))).thenAnswer(inv->inv.getArgument(0));
  var svc=new SolicitareWorkflowServiceImpl(sr,cr,ur,cat,new SolicitareFactory(),new DomainValidationServiceImpl(),mock(AuditService.class));
  svc.atribuieSolicitare(1L,2L,LocalDateTime.now().plusDays(1),3L);
  assertEquals(StareSolicitare.ALOCATA,s.getStare());assertEquals(1,s.getIstoric().size());
  svc.actualizeazaStare(1L,StareSolicitare.IN_LUCRU,"Inceput",2L);
  assertEquals(2,s.getIstoric().size());
 }
}
