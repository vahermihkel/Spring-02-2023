package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.database.Person;
import ee.mihkel.webshop.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
