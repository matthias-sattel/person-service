package org.goafabric.personservice.logic;

import org.goafabric.personservice.persistence.PersonRepository;
import org.goafabric.personservice.service.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PersonLogic {
    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private PersonRepository personRepository;

    public Person getById(String id) {
        return personMapper.map(personRepository.findById(id).get());
    }

    public List<Person> findAll() {
        return personMapper.map(StreamSupport.stream(personRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList()));

    }

    public List<Person> findByFirstName(String firstName) {
        return personMapper.map(personRepository.findByFirstName(firstName));
    }

    public List<Person> findByLastName(String lastName) {
        return personMapper.map(personRepository.findByLastName(lastName));
    }

    public Person save(Person person) {
        return personMapper.map(personRepository.save(personMapper.map(person)));
    }

}
