package org.goafabric.personservice.logic;

import org.goafabric.personservice.adapter.CalleeServiceAdapter;
import org.goafabric.personservice.controller.dto.Person;
import org.goafabric.personservice.crossfunctional.DurationLog;
import org.goafabric.personservice.persistence.PersonRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@DurationLog
public class PersonLogic {
    private final PersonMapper personMapper;

    private final PersonRepository personRepository;

    private final CalleeServiceAdapter calleeServiceAdapter;

    public PersonLogic(PersonMapper personMapper, PersonRepository personRepository, CalleeServiceAdapter calleeServiceAdapter) {
        this.personMapper = personMapper;
        this.personRepository = personRepository;
        this.calleeServiceAdapter = calleeServiceAdapter;
    }

    public Person getById(String id) {
        return personMapper.map(
                personRepository.findById(id).get());
    }

    public List<Person> findAll() {
        return personMapper.map(
                personRepository.findAll());
    }

    public List<Person> findByFirstName(String firstName) {
        return personMapper.map(
                personRepository.findByFirstName(firstName));
    }

    public List<Person> findByLastName(String lastName) {
        return personMapper.map(
                personRepository.findByLastName(lastName));
    }

    public Person save(Person person) {
        return personMapper.map(personRepository.save(
                personMapper.map(person)));
    }

    public Person sayMyName(String name) {
        return Person.builder().firstName(
                calleeServiceAdapter.sayMyName(name).getMessage()).build();
    }
}
