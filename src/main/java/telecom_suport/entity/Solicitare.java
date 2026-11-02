package telecom_suport.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "solicitari")
public class Solicitare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Numarul solicitarii este obligatoriu")
    @Column(nullable = false, unique = true)
    private String numar;

    @NotBlank(message = "Descrierea este obligatorie")
    @Column(nullable = false, length = 2000)
    private String descriere;
    @OneToMany(
            mappedBy = "solicitare",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<IstoricSolicitare> istoric = new ArrayList<>();

    @NotNull(message = "Clientul este obligatoriu")
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "responsabil_id")
    private Utilizator responsabil;

    @NotNull(message = "Categoria este obligatorie")
    @ManyToOne(optional = false)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Canal canal;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Prioritate prioritate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StareSolicitare stare = StareSolicitare.NOUA;

    private LocalDateTime dataCrearii;

    private LocalDateTime termen;

    private LocalDateTime dataSolutionarii;

    @Column(length = 2000)
    private String solutie;

    public Solicitare() {

    }public List<IstoricSolicitare> getIstoric() {
        return List.copyOf(istoric);
    }

    public void adaugaIstoric(IstoricSolicitare modificare) {
        if (modificare == null) {
            throw new IllegalArgumentException(
                    "Modificarea nu poate fi null"
            );
        }

        modificare.setSolicitare(this);
        istoric.add(modificare);
    }
    public void atribuie(Utilizator responsabil, LocalDateTime termen, Utilizator autor) {

        if (responsabil == null || autor == null) {
            throw new IllegalArgumentException("Responsabilul si autorul sunt obligatorii");
        }

        if (!responsabil.isActiv()) {
            throw new IllegalArgumentException("Responsabilul trebuie sa fie activ");
        }

        if (termen == null || !termen.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Termenul trebuie sa fie in viitor");
        }

        if (stare != StareSolicitare.NOUA) {
            throw new IllegalArgumentException("Solicitarea nu poate fi atribuita");
        }

        StareSolicitare stareVeche = this.stare;

        this.responsabil = responsabil;
        this.termen = termen;
        this.stare = StareSolicitare.ALOCATA;

        IstoricSolicitare modificare = new IstoricSolicitare();

        modificare.setStareVeche(stareVeche);
        modificare.setStareNoua(this.stare);
        modificare.setAutor(autor);
        modificare.setObservatii("Solicitare atribuita");
        modificare.setDataModificarii(LocalDateTime.now());

        adaugaIstoric(modificare);
    }
    public void schimbaStarea(StareSolicitare stareNoua, String observatii, Utilizator autor) {

        if (stareNoua == null || autor == null) {
            throw new IllegalArgumentException("Starea si autorul sunt obligatorii");
        }

        boolean permis = false;

        if (stare == StareSolicitare.ALOCATA &&
                stareNoua == StareSolicitare.IN_LUCRU) {
            permis = true;
        }

        if (stare == StareSolicitare.IN_LUCRU &&
                (stareNoua == StareSolicitare.IN_ASTEPTARE ||
                        stareNoua == StareSolicitare.SOLUTIONATA)) {
            permis = true;
        }

        if (stare == StareSolicitare.IN_ASTEPTARE &&
                stareNoua == StareSolicitare.IN_LUCRU) {
            permis = true;
        }

        if (stare == StareSolicitare.SOLUTIONATA &&
                stareNoua == StareSolicitare.INCHISA) {
            permis = true;
        }

        if (!permis) {
            throw new IllegalArgumentException("Schimbarea starii nu este permisa");
        }

        if (stareNoua == StareSolicitare.SOLUTIONATA) {
            throw new IllegalArgumentException(
                    "Pentru solutionare trebuie folosita metoda solutioneaza"
            );
        }

        StareSolicitare stareVeche = this.stare;

        this.stare = stareNoua;

        IstoricSolicitare modificare = new IstoricSolicitare();

        modificare.setStareVeche(stareVeche);
        modificare.setStareNoua(stareNoua);
        modificare.setAutor(autor);
        modificare.setObservatii(observatii);
        modificare.setDataModificarii(LocalDateTime.now());

        adaugaIstoric(modificare);
    }
    public void solutioneaza(String solutie, Utilizator autor) {

        if (solutie == null || solutie.isBlank()) {
            throw new IllegalArgumentException("Solutia este obligatorie");
        }

        if (autor == null) {
            throw new IllegalArgumentException("Autorul este obligatoriu");
        }

        if (stare != StareSolicitare.IN_LUCRU) {
            throw new IllegalArgumentException("Solicitarea nu poate fi solutionata");
        }

        StareSolicitare stareVeche = this.stare;

        this.solutie = solutie;
        this.stare = StareSolicitare.SOLUTIONATA;
        this.dataSolutionarii = LocalDateTime.now();

        IstoricSolicitare modificare = new IstoricSolicitare();

        modificare.setStareVeche(stareVeche);
        modificare.setStareNoua(this.stare);
        modificare.setAutor(autor);
        modificare.setObservatii("Solicitare solutionata");
        modificare.setDataModificarii(LocalDateTime.now());

        adaugaIstoric(modificare);
    }

    public Long getId() { return id; }
    public Utilizator getResponsabil() { return responsabil; }
    public StareSolicitare getStare() { return stare; }
    public LocalDateTime getTermen() { return termen; }
    public LocalDateTime getDataSolutionarii() { return dataSolutionarii; }
    public String getSolutie() { return solutie; }

    public String getNumar() {
        return numar;
    }

    public void setNumar(String numar) {
        new telecom_suport.valueobject.NumarSolicitare(numar);
        this.numar = numar;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        if (categorie == null) {
            throw new IllegalArgumentException("Categoria este obligatorie");
        }
        this.categorie = categorie;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

    public Prioritate getPrioritate() {
        return prioritate;
    }

    public void setPrioritate(Prioritate prioritate) {
        this.prioritate = prioritate;
    }

    public LocalDateTime getDataCrearii() {
        return dataCrearii;
    }

    public void setDataCrearii(LocalDateTime dataCrearii) {
        this.dataCrearii = dataCrearii;
    }
}
