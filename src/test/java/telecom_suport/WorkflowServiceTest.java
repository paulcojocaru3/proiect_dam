package telecom_suport;

import org.junit.jupiter.api.Test;
import telecom_suport.domain.*;
import telecom_suport.service.*;
import telecom_suport.factory.*;
import telecom_suport.repository.SolicitareRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class WorkflowServiceTest {

 private final SolicitareRepository repository =
         mock(SolicitareRepository.class);

 private final SolicitareWorkflowService svc =
         new SolicitareWorkflowServiceImpl(
                 new SolicitareFactory(),
                 new DomainValidationServiceImpl(),
                 new AuditServiceImpl(),
                 repository
         );

 {
  when(repository.save(any(Solicitare.class)))
          .thenAnswer(invocation -> invocation.getArgument(0));
 }

 @Test
 void inregistreazaSolicitareNoua() {

  var s = svc.inregistreazaSolicitare(
          new Client(),
          "Internet indisponibil",
          null,
          Canal.TELEFON,
          Prioritate.MEDIE
  );

  assertEquals(StareSolicitare.NOUA, s.getStare());
  assertNotNull(s.getNumar());
 }

 @Test
 void atribuieSiInregistreazaIstoric() {

  var s = svc.inregistreazaSolicitare(
          new Client(),
          "Internet indisponibil",
          null,
          Canal.TELEFON,
          Prioritate.MEDIE
  );

  var u = new Utilizator();

  svc.atribuieSolicitare(
          s,
          u,
          LocalDateTime.now().plusDays(1),
          u
  );

  assertEquals(StareSolicitare.ALOCATA, s.getStare());
  assertEquals(1, s.getIstoric().size());
 }
}