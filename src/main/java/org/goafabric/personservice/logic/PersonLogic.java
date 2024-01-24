package org.goafabric.personservice.logic;

import org.goafabric.personservice.controller.dto.Person;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class PersonLogic {

    private final PersonRepository personRepository;

    private final CalleeService calleeService;

    public PersonLogic(PersonRepository personRepository, CalleeService calleeService) {
        this.personRepository = personRepository;
        this.calleeService = calleeService;
    }

    public Person getById(String id) {
        return personRepository.getById(id);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public List<Person> findByFirstName(String firstName) {
        return personRepository.findByFirstName(firstName);
    }

    public List<Person> findByLastName(String lastName) {
        return personRepository.findByLastName(lastName);
    }

    public List<Person> findByStreet(String street) {
        return personRepository.findByStreet(street);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Person sayMyName(String name) {
        return new Person(null, null,
                calleeService.sayMyName(name), "", null);
    }
}
