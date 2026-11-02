package telecom_suport.computation;

import telecom_suport.entity.*;
import telecom_suport.valueobject.PerioadaRaportare;
import java.time.Duration;
import java.util.*;
public interface SolicitareComputationService {
 Optional<Duration> durataSolutionarii(Solicitare solicitare);
 Optional<Duration> timpMediu(PerioadaRaportare perioada,Long categorieId);
 Optional<Double> pondereInTermen(PerioadaRaportare perioada,Long categorieId);
 long numarRestante(PerioadaRaportare perioada,Long categorieId);
}
