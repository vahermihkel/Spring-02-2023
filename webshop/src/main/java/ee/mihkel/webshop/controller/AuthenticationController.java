package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.database.Person;
import ee.mihkel.webshop.model.request.LoginData;
import ee.mihkel.webshop.model.request.Token;
import ee.mihkel.webshop.repository.PersonRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
public class AuthenticationController {

    @Autowired
    PersonRepository personRepository;

    // localhost:8080/signup    POST
    @PostMapping("signup")
    public ResponseEntity<List<Person>> addPerson(@RequestBody Person person) {
        if (person.getPersonalCode() == null || personRepository.findById(person.getPersonalCode()).isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(person.getPassword());
            person.setPassword(hashedPassword);
            personRepository.save(person);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(personRepository.findAll());
    }

    @PostMapping("login")
    public ResponseEntity<Token> login(@RequestBody LoginData loginData) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Person person = personRepository.findPersonByEmail(loginData.getEmail());

        if (!passwordEncoder.matches(loginData.getPassword(), person.getPassword())) {
            return ResponseEntity.badRequest().body(null);
        }

        Token token = new Token();

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime oneHourLater = now.plusHours(1);

        Date dateOneHourLater = java.util.Date.from(oneHourLater.atZone(java.time.ZoneId.systemDefault()).toInstant());

        String jwtsToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "super-secret-key")
                .setExpiration(dateOneHourLater)
                .setIssuer("mihkel-webshop")
                .setSubject(person.getPersonalCode())
                .compact();

        token.setToken(jwtsToken);

        return ResponseEntity.ok().body(token);
    }

}
