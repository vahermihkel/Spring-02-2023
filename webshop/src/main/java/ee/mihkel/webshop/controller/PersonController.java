package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.database.Person;
import ee.mihkel.webshop.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("person")
    public ResponseEntity<List<Person>> getCategories() {
        return ResponseEntity.ok().body(personRepository.findAll());
    }

    @GetMapping("person/{personalCode}")
    public ResponseEntity<Person> getPerson(@PathVariable String personalCode) {
        return ResponseEntity.ok().body(personRepository.findById(personalCode).get());
    }

    @DeleteMapping("person/{personalCode}")
    public ResponseEntity<List<Person>> deletePerson(@PathVariable String personalCode) {
        personRepository.deleteById(personalCode);
        return ResponseEntity.ok().body(personRepository.findAll());
    }

    @PostMapping("person")
    public ResponseEntity<List<Person>> addPerson(@RequestBody Person person) {
        if (person.getPersonalCode() == null || personRepository.findById(person.getPersonalCode()).isEmpty()) {
            personRepository.save(person);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(personRepository.findAll());
    }

    @PutMapping("person")
    public ResponseEntity<List<Person>> editPerson(@RequestBody Person person) {
        if (personRepository.findById(person.getPersonalCode()).isPresent()) {
            personRepository.save(person);
        }
        return ResponseEntity.ok().body(personRepository.findAll());
    }
}
