# Telecom Support

Aplicație Java cu Spring Boot pentru gestionarea solicitărilor de suport ale unei firme de telecomunicații. Proiectul conține modelul de domeniu și servicii pentru înregistrarea, atribuirea și actualizarea solicitărilor.

## Funcționalități

- Înregistrarea solicitărilor cu număr unic, client, categorie, canal și prioritate.
- Atribuirea către un angajat activ, cu termen și istoric.
- Actualizarea stării și observațiilor, cu verificarea rolului și a responsabilului.
- Soluționarea cu descriere și dată de soluționare.
- Validarea tranzițiilor și calcule pentru durată, respectarea termenului și solicitări restante.

## Rulare

Necesită Java 21 și Maven:

```bash
mvn spring-boot:run
```

Rulează testele cu:

```bash
mvn test
```

Proiectul folosește Spring Data JPA pentru persistență. Datele nu sunt stocate în RAM: configurația curentă cere PostgreSQL, iar Hibernate verifică schema existentă la pornire. Nu este inclusă o bază de date în memorie. Pentru configurare se pot folosi variabilele `DB_URL`, `DB_USER` și `DB_PASSWORD`.

Momentan aplicația conține modelul și serviciile de business; nu include interfață de utilizator sau autentificare.
