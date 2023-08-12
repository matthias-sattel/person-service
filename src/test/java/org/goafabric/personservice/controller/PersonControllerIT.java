package org.goafabric.personservice.controller;

import org.goafabric.personservice.controller.vo.Address;
import org.goafabric.personservice.controller.vo.Person;
import org.goafabric.personservice.repository.PersonRepository;
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
    private PersonRepository personRepository;

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
    }

    @Test
    public void findByLastName() {
        List<Person> persons = personController.findByLastName("Simpson");
        assertThat(persons).isNotNull().hasSize(2);
        assertThat(persons.get(0).lastName()).isEqualTo("Simpson");
    }

    @Test
    void save() {
        final Person person = personController.save(
                new Person("null",
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

        personRepository.deleteById(person.id());
    }

    private Address createAddress(String street) {
        return new Address(null, null,
                street, "Springfield");
    }

}