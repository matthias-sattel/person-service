package org.goafabric.personservice.service;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.personservice.logic.PersonLogic;
import org.goafabric.personservice.service.dto.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/persons",
        produces = "application/json")

@RestController
@Slf4j
public class PersonService {
    @Autowired
    private PersonLogic personLogic;

    @GetMapping("getById/{id}")
    public Person getById(@PathVariable("id") String id) {
        return personLogic.getById(id);
    }

    @GetMapping("findAll")
    public List<Person> findAll() {
        return personLogic.findAll();
    }

    @GetMapping("findByFirstName")
    public List<Person> findByFirstName(@RequestParam String firstName) {
        return personLogic.findByFirstName(firstName);
    }

    @GetMapping("findByLastName")
    public List<Person> findByLastName(@RequestParam String lastName) {
        return personLogic.findByLastName(lastName);
    }


    @PostMapping(value = "save", consumes = "application/json")
    public Person save(@RequestBody Person person) {
        return personLogic.save(person);
    }

}
