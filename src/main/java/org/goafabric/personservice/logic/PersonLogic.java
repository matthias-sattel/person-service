package org.goafabric.personservice.logic;

import org.goafabric.personservice.adapter.CalleeServiceAdapter;
import org.goafabric.personservice.persistence.PersonRepository;
import org.goafabric.personservice.service.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class PersonLogic {
    @Autowired
    PersonMapper personMapper;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CalleeServiceAdapter calleeServiceAdapter;

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
