package telecom_suport.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import telecom_suport.domain.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IstoricSolicitareRepositoryTest {

    @Autowired
    private SolicitareRepository solicitareRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private IstoricSolicitareRepository istoricRepository;

    // TEST 1: Salvarea automata a istoricului prin CascadeType.ALL
    @Test
    void salveazaIstoricAutomat() {

        // 1. Cream clientul
        Client client = new Client();
        client.setNume("Popescu");
        client.setPrenume("Ana");
        client.setEmail("ana@test.ro");
        client.setTelefon("0712345678");

        Client clientSalvat = clientRepository.save(client);

        // 2. Cream solicitarea
        Solicitare solicitare = new Solicitare();
        solicitare.setNumar("SOL-005");
        solicitare.setDescriere("Problema conexiune internet");
        solicitare.setClient(clientSalvat);
        solicitare.setCanal(Canal.TELEFON);
        solicitare.setPrioritate(Prioritate.RIDICATA);

        // 3. Cream inregistrarea din istoric
        IstoricSolicitare istoric = new IstoricSolicitare();
        istoric.setStareVeche(StareSolicitare.NOUA);
        istoric.setStareNoua(StareSolicitare.ALOCATA);
        istoric.setObservatii("Solicitare atribuita");

        // 4. Asociem istoricul cu solicitarea
        solicitare.adaugaIstoric(istoric);

        // 5. Salvam DOAR solicitarea
        solicitareRepository.saveAndFlush(solicitare);

        // 6. Verificam salvarea automata a istoricului
        assertNotNull(istoric.getId());
        assertEquals(1, istoricRepository.count());
    }
    // TEST 2: Recuperarea istoricului din baza de date
    @Test
    void recupereazaIstoricDinBazaDeDate() {

        // 1. Cream si salvam clientul
        Client client = new Client();
        client.setNume("Ionescu");
        client.setPrenume("Maria");
        client.setEmail("maria@test.ro");
        client.setTelefon("0723456789");

        Client clientSalvat = clientRepository.save(client);

        // 2. Cream solicitarea
        Solicitare solicitare = new Solicitare();
        solicitare.setNumar("SOL-006");
        solicitare.setDescriere("Problema facturare");
        solicitare.setClient(clientSalvat);
        solicitare.setCanal(Canal.EMAIL);
        solicitare.setPrioritate(Prioritate.MEDIE);

        // 3. Cream istoricul
        IstoricSolicitare istoric = new IstoricSolicitare();
        istoric.setStareVeche(StareSolicitare.NOUA);
        istoric.setStareNoua(StareSolicitare.ALOCATA);
        istoric.setObservatii("Solicitare repartizata");

        solicitare.adaugaIstoric(istoric);

        // 4. Salvam solicitarea si istoricul
        solicitareRepository.saveAndFlush(solicitare);

        // 5. Recuperam istoricul dupa ID
        IstoricSolicitare istoricGasit = istoricRepository
                .findById(istoric.getId())
                .orElseThrow();

        // 6. Verificam datele recuperate
        assertEquals("Solicitare repartizata",
                istoricGasit.getObservatii());

        assertEquals(StareSolicitare.NOUA,
                istoricGasit.getStareVeche());

        assertEquals(StareSolicitare.ALOCATA,
                istoricGasit.getStareNoua());

        assertEquals("SOL-006",
                istoricGasit.getSolicitare().getNumar());
    }
}