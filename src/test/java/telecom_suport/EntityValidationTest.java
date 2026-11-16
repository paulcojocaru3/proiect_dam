package telecom_suport;
import org.junit.jupiter.api.Test;
import telecom_suport.domain.*;
import telecom_suport.service.*;
import static org.junit.jupiter.api.Assertions.*;
class EntityValidationTest {
 private final DomainValidationService v=new DomainValidationServiceImpl();
 @Test void responsabilInactivEsteRespins(){Utilizator u=new Utilizator();u.setActiv(false);assertThrows(UtilizatorInactivException.class,()->v.valideazaResponsabil(u));}
 @Test void solutionareFaraTextEsteRespinsa(){assertThrows(RegulaBusinessException.class,()->v.valideazaSolutionarea(new Solicitare()," "));}
 @Test void tranzitieInvalidaEsteRespinsa(){assertThrows(RegulaBusinessException.class,()->v.valideazaTranzitie(StareSolicitare.NOUA,StareSolicitare.INCHISA));}
}
