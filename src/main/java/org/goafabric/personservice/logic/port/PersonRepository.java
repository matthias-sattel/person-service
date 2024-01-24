package org.goafabric.personservice.logic.port;

import org.goafabric.personservice.controller.dto.Person;

import java.util.List;

public interface PersonRepository {
    Person getById(String id);

    List<Person> findAll();

    List<Person> findByFirstName(String firstName);

    List<Person> findByLastName(String lastName);

    List<Person> findByStreet(String street);

    Person save(Person person);
}
