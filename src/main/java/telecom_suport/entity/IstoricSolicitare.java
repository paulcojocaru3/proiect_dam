package telecom_suport.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "istoric_solicitari")
public class IstoricSolicitare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "solicitare_id", nullable = false)
    private Solicitare solicitare;

    @Enumerated(EnumType.STRING)
    private StareSolicitare stareVeche;

    @Enumerated(EnumType.STRING)
    private StareSolicitare stareNoua;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Utilizator autor;

    @Column(length = 2000)
    private String observatii;

    @Column(nullable = false)
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
