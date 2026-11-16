package telecom_suport.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import telecom_suport.domain.Categorie;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategorieRepositoryTest {

    @Autowired
    private CategorieRepository categorieRepository;

    // TEST 1: Verificarea initializarii repository-ului
    @Test
    void repositoryEsteDisponibil() {
        assertNotNull(categorieRepository);
    }

    // TEST 2: Salvarea unei categorii
    @Test
    void salveazaCategorie() {

        Categorie categorie = new Categorie();
        categorie.setDenumire("Probleme tehnice");
        categorie.setDescriere("Solicitari privind serviciile tehnice");

        Categorie salvata = categorieRepository.save(categorie);

        assertNotNull(salvata.getId());
        assertEquals("Probleme tehnice", salvata.getDenumire());
    }

    // TEST 3: Recuperarea unei categorii dupa ID
    @Test
    void cautaCategorieDupaId() {

        Categorie categorie = new Categorie();
        categorie.setDenumire("Facturare");
        categorie.setDescriere("Probleme privind facturile");

        Categorie salvata = categorieRepository.save(categorie);

        Categorie gasita = categorieRepository
                .findById(salvata.getId())
                .orElseThrow();

        assertEquals("Facturare", gasita.getDenumire());
    }

    // TEST 4: Stergerea unei categorii
    @Test
    void stergeCategorie() {

        Categorie categorie = new Categorie();
        categorie.setDenumire("Servicii");
        categorie.setDescriere("Informatii despre servicii");

        Categorie salvata = categorieRepository.save(categorie);

        categorieRepository.deleteById(salvata.getId());

        assertFalse(categorieRepository.existsById(salvata.getId()));
    }
}