package org.goafabric.personservice.controller;

import org.goafabric.personservice.controller.dto.Address;
import org.goafabric.personservice.controller.dto.Person;
import org.goafabric.personservice.repository.PersonJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerIT {

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonJpaRepository personRepository;

    @Test
    public void findById() {
        List<Person> persons = personController.findAll();
        assertThat(persons).isNotNull().hasSize(3);

        final Person person
                = personController.getById(persons.get(0).id());
        assertThat(person).isNotNull();
        assertThat(person.firstName()).isEqualTo(persons.get(0).firstName());
        assertThat(person.lastName()).isEqualTo(persons.get(0).lastName());
    }

    @Test
    public void findAll() {
        assertThat(personController.findAll()).isNotNull().hasSize(3);

        assertThat(personController.findAll()).isNotNull().hasSize(3);
    }

    @Test
    public void findByFirstName() {
        List<Person> persons = personController.findByFirstName("Monty");
        assertThat(persons).isNotNull().hasSize(1);
        assertThat(persons.get(0).firstName()).isEqualTo("Monty");
        assertThat(persons.get(0).lastName()).isEqualTo("Burns");
        assertThat(persons.get(0).address()).isNotEmpty();
    }

    @Test
    public void findByLastName() {
        List<Person> persons = personController.findByLastName("Simpson");
        assertThat(persons).isNotNull().hasSize(2);
        assertThat(persons.get(0).lastName()).isEqualTo("Simpson");
        assertThat(persons.get(0).address()).isNotEmpty();
    }

    @Test
    public void findByAddressCity() {
        List<Person> persons = personController.findByStreet("Evergreen Terrace");
        assertThat(persons).isNotNull().isNotEmpty();
        assertThat(persons.get(0).address().get(0).street()).startsWith("Evergreen Terrace No.");
        //assertThat(persons.get(0).lastName()).isEqualTo("Simpson");
    }

    @Test
    void save() {
        final Person person = personController.save(
                new Person(null,
                        null,
                        "Homer",
                        "Simpson",
                        List.of(
                                createAddress("Evergreen Terrace"),
                                createAddress("Everblue Terrace"))
                ));

        assertThat(person).isNotNull();

        var person2 = personController.getById(person.id());
        assertThat(person2).isNotNull();
        assertThat(person2.address()).hasSize(2);

        //update
        assertThat(personController.save(
                new Person(person.id(), person.version(), person.firstName(), person.lastName(), person.address())).id()).isEqualTo(person.id());


        personRepository.deleteById(person.id());
    }

    private Address createAddress(String street) {
        return new Address(null, null,
                street, "Springfield");
    }

}