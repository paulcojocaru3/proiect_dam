package telecom_suport.factory;

import org.springframework.stereotype.Component;
import telecom_suport.entity.*;
import java.time.LocalDateTime;
@Component
public class SolicitareFactory {
 public Solicitare noua(Client client, String descriere, Canal canal, Prioritate prioritate, String numar) {
  if (client == null || descriere == null || descriere.isBlank() || canal == null || prioritate == null || numar == null || numar.isBlank()) throw new IllegalArgumentException("Campuri obligatorii lipsa");
  Solicitare s = new Solicitare(); s.setClient(client); s.setDescriere(descriere); s.setCanal(canal); s.setPrioritate(prioritate); s.setNumar(numar); s.setDataCrearii(LocalDateTime.now()); return s;
 }
}
