package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.database.Person;
import ee.mihkel.webshop.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping("person")
    public List<Person> getCategories() {
        return personRepository.findAll();
    }

    @GetMapping("person/{personalCode}")
    public Person getPerson(@PathVariable String personalCode) {
        return personRepository.findById(personalCode).get();
    }

    @DeleteMapping("person/{personalCode}")
    public List<Person> deletePerson(@PathVariable String personalCode) {
        personRepository.deleteById(personalCode);
        return personRepository.findAll();
    }

    @PostMapping("person")
    public List<Person> addPerson(@RequestBody Person person) {
        if (person.getPersonalCode() == null || personRepository.findById(person.getPersonalCode()).isEmpty()) {
            personRepository.save(person);
        }
        return personRepository.findAll();
    }

    @PutMapping("person")
    public List<Person> editPerson(@RequestBody Person person) {
        if (personRepository.findById(person.getPersonalCode()).isPresent()) {
            personRepository.save(person);
        }
        return personRepository.findAll();
    }
}
