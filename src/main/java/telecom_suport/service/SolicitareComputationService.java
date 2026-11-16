package telecom_suport.service;
import telecom_suport.domain.*;
import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
public interface SolicitareComputationService {
 Optional<Duration> durataSolutionarii(Solicitare solicitare);
 Optional<Duration> timpMediu(Collection<Solicitare> solicitari, PerioadaRaportare perioada, Long categorieId);
 Optional<Double> pondereInTermen(Collection<Solicitare> solicitari, PerioadaRaportare perioada, Long categorieId);
 long numarRestante(Collection<Solicitare> solicitari, PerioadaRaportare perioada, Long categorieId);
}
