package org.goafabric.personservice.logic;

import lombok.NonNull;
import org.goafabric.personservice.adapter.CalleeServiceAdapter;
import org.goafabric.personservice.crossfunctional.DurationLog;
import org.goafabric.personservice.persistence.PersonRepository;
import org.goafabric.personservice.controller.dto.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@DurationLog
public class PersonLogic {
    @Autowired
    PersonMapper personMapper;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CalleeServiceAdapter calleeServiceAdapter;

    public Person getById(@NonNull String id) {
        return personMapper.map(
                personRepository.findById(id).get());
    }

    public List<Person> findAll() {
        return personMapper.map(
                personRepository.findAll());
    }

    public List<Person> findByFirstName(@NonNull String firstName) {
        return personMapper.map(
                personRepository.findByFirstName(firstName));
    }

    public List<Person> findByLastName(@NonNull String lastName) {
        return personMapper.map(
                personRepository.findByLastName(lastName));
    }

    public Person save(@NonNull Person person) {
        return personMapper.map(personRepository.save(
                personMapper.map(person)));
    }

    public Person sayMyName(@NonNull String name) {
        return Person.builder().firstName(
                calleeServiceAdapter.sayMyName(name).getMessage()).build();
    }
}
