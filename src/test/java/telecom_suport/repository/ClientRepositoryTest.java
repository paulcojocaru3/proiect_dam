package telecom_suport.repository;
import telecom_suport.domain.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void repositoryEsteDisponibil() {
        assertNotNull(clientRepository);
    }
    @Test
    void salveazaSiCautaClient() {

        // 1. Crearea clientului
        Client client = new Client();
        client.setNume("Popescu");
        client.setPrenume("Ana");
        client.setEmail("ana@test.ro");
        client.setTelefon("0712345678");

        // 2. Salvarea clientului in baza de date
        Client clientSalvat = clientRepository.save(client);

        // 3. Verificarea ID-ului generat
        assertNotNull(clientSalvat.getId());

        // 4. Cautarea clientului dupa ID
        Client clientGasit = clientRepository
                .findById(clientSalvat.getId())
                .orElseThrow();

        // 5. Verificarea datelor recuperate
        assertEquals("Popescu", clientGasit.getNume());
        assertEquals("Ana", clientGasit.getPrenume());
    }

}