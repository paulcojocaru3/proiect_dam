package telecom_suport;
import org.junit.jupiter.api.Test;
import telecom_suport.domain.*;
import telecom_suport.service.*;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class ComputationServiceTest {
 private final SolicitareComputationService calc=new SolicitareComputationServiceImpl();
 @Test void calculeazaDurataDoarPentruSolicitariSolutionate(){assertTrue(calc.durataSolutionarii(new Solicitare()).isEmpty());}
 @Test void faraDateNuExistaMedie(){assertTrue(calc.timpMediu(List.of(),new PerioadaRaportare(LocalDate.now().minusDays(1),LocalDate.now()),null).isEmpty());}
 @Test void faraDateNuExistaPondere(){assertTrue(calc.pondereInTermen(List.of(),new PerioadaRaportare(LocalDate.now().minusDays(1),LocalDate.now()),null).isEmpty());}
}
