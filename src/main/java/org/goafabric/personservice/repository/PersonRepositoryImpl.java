package org.goafabric.personservice.repository;

import org.goafabric.personservice.controller.dto.Person;
import org.goafabric.personservice.logic.port.PersonRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonRepositoryImpl implements PersonRepository {
    private final PersonJpaRepository personJpaRepository;
    private final PersonMapper personMapper;

    public PersonRepositoryImpl(PersonJpaRepository personJpaRepository, PersonMapper personMapper) {
        this.personJpaRepository = personJpaRepository;
        this.personMapper = personMapper;
    }

    @Override
    public Person getById(String id) {
        return personMapper.map(
                personJpaRepository.findById(id).get());
    }

    @Override
    public List<Person> findAll() {
        return personMapper.map(
                personJpaRepository.findAll());
    }

    @Override
    public List<Person> findByFirstName(String firstName) {
        return personMapper.map(
                personJpaRepository.findByFirstName(firstName));
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        return personMapper.map(
                personJpaRepository.findByLastName(lastName));
    }

    @Override
    public List<Person> findByStreet(String street) {
        return personMapper.map(
                personJpaRepository.findByAddress_StreetContainsIgnoreCase(street));
    }

    @Override
    public Person save(Person person) {
        return personMapper.map(personJpaRepository.save(
                personMapper.map(person)));
    }
}
