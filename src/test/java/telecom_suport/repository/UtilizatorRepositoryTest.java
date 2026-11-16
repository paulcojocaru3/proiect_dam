package telecom_suport.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import telecom_suport.domain.Utilizator;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UtilizatorRepositoryTest {

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    // TEST 1: Verificarea initializarii repository-ului
    @Test
    void repositoryEsteDisponibil() {
        assertNotNull(utilizatorRepository);
    }

    // TEST 2: Salvarea unui utilizator
    @Test
    void salveazaUtilizator() {

        Utilizator utilizator = new Utilizator();
        utilizator.setNume("Andrei Popescu");
        utilizator.setEmail("andrei@test.ro");

        Utilizator salvat = utilizatorRepository.save(utilizator);

        assertNotNull(salvat.getId());
    }

    // TEST 3: Recuperarea unui utilizator dupa ID
    @Test
    void cautaUtilizatorDupaId() {

        Utilizator utilizator = new Utilizator();
        utilizator.setNume("Maria Ionescu");
        utilizator.setEmail("maria@test.ro");

        Utilizator salvat = utilizatorRepository.save(utilizator);

        Utilizator gasit = utilizatorRepository
                .findById(salvat.getId())
                .orElseThrow();

        assertEquals("Maria Ionescu", gasit.getNume());
    }

    // TEST 4: Stergerea unui utilizator
    @Test
    void stergeUtilizator() {

        Utilizator utilizator = new Utilizator();
        utilizator.setNume("Elena Popa");
        utilizator.setEmail("elena@test.ro");

        Utilizator salvat = utilizatorRepository.save(utilizator);

        utilizatorRepository.deleteById(salvat.getId());

        assertFalse(utilizatorRepository.existsById(salvat.getId()));
    }
}