package telecom_suport.domain;
public record Email(String valoare) {
 public Email { if (valoare == null || !valoare.matches("^[^@ ]+@[^@ ]+\\.[^@ ]+$")) throw new IllegalArgumentException("Email invalid"); }
}
