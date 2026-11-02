package telecom_suport.computation;

import org.springframework.stereotype.Service;
import telecom_suport.entity.*;
import telecom_suport.repository.SolicitareRepository;
import telecom_suport.valueobject.PerioadaRaportare;
import java.time.*;
import java.util.*;
@Service
public class SolicitareComputationServiceImpl implements SolicitareComputationService {
 private final SolicitareRepository repository;
 public SolicitareComputationServiceImpl(SolicitareRepository repository) { this.repository=repository; }
 public Optional<Duration> durataSolutionarii(Solicitare s) {
  if(s==null || s.getDataCrearii()==null || s.getDataSolutionarii()==null) return Optional.empty();
  return Optional.of(Duration.between(s.getDataCrearii(),s.getDataSolutionarii()));
 }
 private List<Solicitare> selectate(PerioadaRaportare p,Long categorieId) {
  return repository.findAll().stream().filter(s->s.getDataCrearii()!=null && p.contine(s.getDataCrearii().toLocalDate()))
   .filter(s->categorieId==null || (s.getCategorie()!=null && categorieId.equals(s.getCategorie().getId()))).toList();
 }
 public Optional<Duration> timpMediu(PerioadaRaportare p,Long categorieId) {
  var durate=selectate(p,categorieId).stream().map(this::durataSolutionarii).flatMap(Optional::stream).toList();
  if(durate.isEmpty()) return Optional.empty();
  return Optional.of(Duration.ofMillis((long)durate.stream().mapToLong(Duration::toMillis).average().orElseThrow()));
 }
 public Optional<Double> pondereInTermen(PerioadaRaportare p,Long categorieId) {
  var eligibile=selectate(p,categorieId).stream().filter(s->s.getDataSolutionarii()!=null && s.getTermen()!=null).toList();
  if(eligibile.isEmpty()) return Optional.empty();
  long inTermen=eligibile.stream().filter(s->!s.getDataSolutionarii().isAfter(s.getTermen())).count();
  return Optional.of(100.0*inTermen/eligibile.size());
 }
 public long numarRestante(PerioadaRaportare p,Long categorieId) {
  return selectate(p,categorieId).stream().filter(s->s.getTermen()!=null && s.getTermen().isBefore(LocalDateTime.now()) && s.getDataSolutionarii()==null && s.getStare()!=StareSolicitare.INCHISA).count();
 }
}
