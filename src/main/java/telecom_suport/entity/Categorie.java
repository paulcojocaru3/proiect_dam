package telecom_suport.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categorii")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Denumirea este obligatorie")
    @Size(min = 2, max = 100)
    private String denumire;

    private String descriere;

    public Categorie() {
    }

    public Categorie(String denumire, String descriere) {
        this.denumire = denumire;
        this.descriere = descriere;
    }

    public Long getId() {
        return id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
}