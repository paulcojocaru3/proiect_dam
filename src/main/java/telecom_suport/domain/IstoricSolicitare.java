package telecom_suport.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.time.LocalDateTime;
@Entity
@Table(name = "istoric_solicitari")
public class IstoricSolicitare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Solicitare solicitare;
    @Enumerated(EnumType.STRING)
    private StareSolicitare stareVeche;
    @Enumerated(EnumType.STRING)
    private StareSolicitare stareNoua;
    @ManyToOne
    private Utilizator autor;
    private String observatii;

    private LocalDateTime dataModificarii;

    public IstoricSolicitare() {
    }

    public Long getId() {
        return id;
    }

    public Solicitare getSolicitare() {
        return solicitare;
    }

    public void setSolicitare(Solicitare solicitare) {
        this.solicitare = solicitare;
    }

    public StareSolicitare getStareVeche() {
        return stareVeche;
    }

    public void setStareVeche(StareSolicitare stareVeche) {
        this.stareVeche = stareVeche;
    }

    public StareSolicitare getStareNoua() {
        return stareNoua;
    }

    public void setStareNoua(StareSolicitare stareNoua) {
        this.stareNoua = stareNoua;
    }

    public Utilizator getAutor() {
        return autor;
    }

    public void setAutor(Utilizator autor) {
        this.autor = autor;
    }

    public String getObservatii() {
        return observatii;
    }

    public void setObservatii(String observatii) {
        this.observatii = observatii;
    }

    public LocalDateTime getDataModificarii() {
        return dataModificarii;
    }

    public void setDataModificarii(LocalDateTime dataModificarii) {
        this.dataModificarii = dataModificarii;
    }
}