package telecom_suport.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import telecom_suport.domain.Client;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EntityRepositoryJPATest {

    @Autowired
    private EntityManager entityManager;

    private EntityRepositoryJPA<Client, Long> repository;

    @BeforeEach
    void pregatesteRepository() {
        repository = new EntityRepositoryJPA<>(Client.class);

        // Injectam EntityManager pentru testare
        org.springframework.test.util.ReflectionTestUtils.setField(
                repository,
                "entityManager",
                entityManager
        );
    }

    private Client creeazaClient(String email) {
        Client client = new Client();
        client.setNume("Popescu");
        client.setPrenume("Ana");
        client.setEmail(email);
        client.setTelefon("0712345678");
        return client;
    }

    // TEST 1: CREATE - Salvarea unui client
    @Test
    void salveazaClient() {

        Client client = creeazaClient("test1@test.ro");

        Client salvat = repository.save(client);

        assertNotNull(salvat.getId());
    }

    // TEST 2: READ - Cautarea unui client dupa ID
    @Test
    void cautaClientDupaId() {

        Client salvat = repository.save(
                creeazaClient("test2@test.ro")
        );

        Optional<Client> gasit = repository.findById(salvat.getId());

        assertTrue(gasit.isPresent());
        assertEquals("Popescu", gasit.get().getNume());
    }

    // TEST 3: READ ALL - Recuperarea tuturor clientilor
    @Test
    void recupereazaTotiClientii() {

        repository.save(creeazaClient("test3@test.ro"));
        repository.save(creeazaClient("test4@test.ro"));

        List<Client> clienti = repository.findAll();

        assertEquals(2, clienti.size());
    }

    // TEST 4: DELETE - Stergerea unui client
    @Test
    void stergeClient() {

        Client salvat = repository.save(
                creeazaClient("test5@test.ro")
        );

        repository.deleteById(salvat.getId());

        Optional<Client> gasit = repository.findById(salvat.getId());

        assertTrue(gasit.isEmpty());
    }
}