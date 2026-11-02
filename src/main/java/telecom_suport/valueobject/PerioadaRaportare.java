package telecom_suport.valueobject;

import java.time.LocalDate;
public record PerioadaRaportare(LocalDate inceput, LocalDate sfarsit) {
 public PerioadaRaportare { if (inceput == null || sfarsit == null || inceput.isAfter(sfarsit)) throw new IllegalArgumentException("Perioada invalida"); }
 public boolean contine(LocalDate data) { return data != null && !data.isBefore(inceput) && !data.isAfter(sfarsit); }
}
