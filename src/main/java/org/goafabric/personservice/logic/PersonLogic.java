package org.goafabric.personservice.logic;

import org.goafabric.personservice.service.dto.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonLogic {
    public Person getById(String id) {
        return new Person();
    }

    public List<Person> findAll() {
        return new ArrayList<>();
    }

    public List<Person> findByFirstName(String firstName) {
        return new ArrayList<>();
    }

    public List<Person> findByLastName(String lastName) {
        return new ArrayList<>();
    }

    public Person save(Person person) {
        return new Person();
    }

}
