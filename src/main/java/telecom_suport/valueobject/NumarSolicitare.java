package telecom_suport.valueobject;

public class NumarSolicitare {

    private final String valoare;

    public NumarSolicitare(String valoare) {

        if (valoare == null || !valoare.matches("REC-[0-9]{4}")) {
            throw new IllegalArgumentException(
                    "Numarul solicitarii trebuie sa aiba formatul REC-0001"
            );
        }

        this.valoare = valoare;
    }

    public String getValoare() {
        return valoare;
    }
}