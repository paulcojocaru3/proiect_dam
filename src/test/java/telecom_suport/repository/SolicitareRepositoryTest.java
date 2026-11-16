package telecom_suport.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import telecom_suport.domain.Client;
import telecom_suport.domain.Solicitare;
import telecom_suport.domain.Canal;
import telecom_suport.domain.Prioritate;
import telecom_suport.domain.StareSolicitare;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SolicitareRepositoryTest {

    @Autowired
    private SolicitareRepository solicitareRepository;
    @Autowired
    private ClientRepository clientRepository;
    // TEST 1: Verificarea initializarii repository-ului
    @Test
    void repositoryEsteDisponibil() {

        assertNotNull(solicitareRepository);
    }
    // TEST 2: Salvarea unei solicitari in baza de date
    @Test
    void salveazaSolicitare() {

        // 1. Cream clientul
        Client client = new Client();
        client.setNume("Popescu");
        client.setPrenume("Ana");
        client.setEmail("ana@test.ro");
        client.setTelefon("0712345678");

        // 2. Salvam clientul
        Client clientSalvat = clientRepository.save(client);

        // 3. Cream solicitarea
        Solicitare solicitare = new Solicitare();
        solicitare.setNumar("SOL-001");
        solicitare.setDescriere("Problema conexiune internet");
        solicitare.setClient(clientSalvat);
        solicitare.setCanal(Canal.TELEFON);
        solicitare.setPrioritate(Prioritate.RIDICATA);

        // 4. Salvam solicitarea
        Solicitare solicitareSalvata = solicitareRepository.save(solicitare);

        // 5. Verificam generarea ID-ului
        assertNotNull(solicitareSalvata.getId());
    }
    // TEST 3: Cautarea unei solicitari dupa numar
    @Test
    void cautaSolicitareDupaNumar() {

        // 1. Cream si salvam clientul
        Client client = new Client();
        client.setNume("Ionescu");
        client.setPrenume("Maria");
        client.setEmail("maria@test.ro");
        client.setTelefon("0723456789");

        Client clientSalvat = clientRepository.save(client);

        // 2. Cream solicitarea
        Solicitare solicitare = new Solicitare();
        solicitare.setNumar("SOL-002");
        solicitare.setDescriere("Problema semnal telefonic");
        solicitare.setClient(clientSalvat);
        solicitare.setCanal(Canal.TELEFON);
        solicitare.setPrioritate(Prioritate.MEDIE);

        // 3. Salvam solicitarea
        solicitareRepository.save(solicitare);

        // 4. Cautam solicitarea dupa numar
        Solicitare rezultat = solicitareRepository.findByNumar("SOL-002");

        // 5. Verificam rezultatul
        assertNotNull(rezultat);
        assertEquals("SOL-002", rezultat.getNumar());
        assertEquals("Problema semnal telefonic", rezultat.getDescriere());
    }
    // TEST 4: Cautarea solicitarilor dupa stare folosind @Query
    @Test
    void cautaSolicitariDupaStare() {

        // 1. Cream si salvam clientul
        Client client = new Client();
        client.setNume("Popa");
        client.setPrenume("Elena");
        client.setEmail("elena@test.ro");
        client.setTelefon("0734567890");

        Client clientSalvat = clientRepository.save(client);

        // 2. Cream prima solicitare
        Solicitare solicitare1 = new Solicitare();
        solicitare1.setNumar("SOL-003");
        solicitare1.setDescriere("Internet nefunctional");
        solicitare1.setClient(clientSalvat);
        solicitare1.setCanal(Canal.TELEFON);
        solicitare1.setPrioritate(Prioritate.RIDICATA);


        // 3. Cream a doua solicitare
        Solicitare solicitare2 = new Solicitare();
        solicitare2.setNumar("SOL-004");
        solicitare2.setDescriere("Problema facturare");
        solicitare2.setClient(clientSalvat);
        solicitare2.setCanal(Canal.EMAIL);
        solicitare2.setPrioritate(Prioritate.MEDIE);

        // 4. Salvam solicitarile
        solicitareRepository.save(solicitare1);
        solicitareRepository.save(solicitare2);

        // 5. Cautam solicitarile cu starea NOUA
        var rezultate = solicitareRepository.findByStare(StareSolicitare.NOUA);

        // 6. Verificam rezultatele
        assertEquals(2, rezultate.size());
        assertTrue(rezultate.stream()
                .anyMatch(s -> s.getNumar().equals("SOL-003")));
        assertTrue(rezultate.stream()
                .anyMatch(s -> s.getNumar().equals("SOL-004")));
    }
    @Test
    void verificaAuditLaActualizare() {

        Client client = new Client();
        client.setNume("Popescu");
        client.setPrenume("Andrei");
        client.setEmail("andrei@test.ro");
        client.setTelefon("0712345678");

        Client clientSalvat = clientRepository.save(client);

        Solicitare solicitare = new Solicitare();
        solicitare.setNumar("SOL-005");
        solicitare.setDescriere("Problema internet");
        solicitare.setClient(clientSalvat);
        solicitare.setCanal(Canal.TELEFON);
        solicitare.setPrioritate(Prioritate.MEDIE);

        Solicitare salvata = solicitareRepository.saveAndFlush(solicitare);

        salvata.setDescriere("Problema internet actualizata");

        solicitareRepository.saveAndFlush(salvata);

        assertEquals(
                "Problema internet actualizata",
                salvata.getDescriere()
        );
    }
}