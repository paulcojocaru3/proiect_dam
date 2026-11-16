package telecom_suport;
import org.junit.jupiter.api.Test;
import telecom_suport.domain.*;
import telecom_suport.factory.SolicitareFactory;
import static org.junit.jupiter.api.Assertions.*;
class AggregateFactoryTest {
 @Test void creareSolicitareCuStareInitialaSiIdentificator() {
  Solicitare s=new SolicitareFactory().noua(new Client(),"Internet indisponibil",Canal.TELEFON,Prioritate.MEDIE,"REC-123");
  assertEquals(StareSolicitare.NOUA,s.getStare()); assertEquals("REC-123",s.getNumar());assertNotNull(s.getDataCrearii());
 }
 @Test void respingeCampuriObligatoriiLipsa() {
  assertThrows(IllegalArgumentException.class,()->new SolicitareFactory().noua(new Client()," ",Canal.TELEFON,Prioritate.MEDIE,"REC-123"));
 }
}
