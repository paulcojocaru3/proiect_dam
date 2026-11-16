package telecom_suport;
import org.junit.jupiter.api.Test;
import telecom_suport.domain.*;
import telecom_suport.service.*;
import static org.junit.jupiter.api.Assertions.*;
class AuditServiceTest {
 @Test void auditRespingeDateIncomplete(){assertThrows(IllegalArgumentException.class,()->new AuditServiceImpl().inregistreaza(null,null,null));}
 @Test void auditAcceptaEvenimentValid(){var u=new Utilizator();var s=new Solicitare();assertDoesNotThrow(()->new AuditServiceImpl().inregistreaza(s,u,"ATRIBUIRE"));}
}
