package org.goafabric.personservice.logic;

import org.goafabric.personservice.controller.dto.Address;
import org.goafabric.personservice.controller.dto.Person;
import org.goafabric.personservice.crossfunctional.HttpInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonLogicIT {
    @Autowired
    private PersonLogic personLogic;

    @Test
    public void findById() {
        HttpInterceptor.setTenantId("0");
        List<Person> persons = personLogic.findAll();
        assertThat(persons).isNotNull().hasSize(3);

        final Person person
                = personLogic.getById(persons.get(0).id());
        assertThat(person).isNotNull();
        assertThat(person.firstName()).isEqualTo(persons.get(0).firstName());
        assertThat(person.lastName()).isEqualTo(persons.get(0).lastName());

        HttpInterceptor.setTenantId("5a2f");
        //assertThatThrownBy(() ->  personLogic.getById(persons.get(0).getId()))
          //      .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void findAll() {
        HttpInterceptor.setTenantId("0");
        assertThat(personLogic.findAll()).isNotNull().hasSize(3);

        HttpInterceptor.setTenantId("5a2f");
        assertThat(personLogic.findAll()).isNotNull().hasSize(3);
    }

    @Test
    public void findByFirstName() {
        HttpInterceptor.setTenantId("0");
        List<Person> persons = personLogic.findByFirstName("Monty");
        assertThat(persons).isNotNull().hasSize(1);
        assertThat(persons.get(0).firstName()).isEqualTo("Monty");
        assertThat(persons.get(0).lastName()).isEqualTo("Burns");

        HttpInterceptor.setTenantId("5a2f");
        assertThat(personLogic.findByFirstName("Monty")).isNotNull().hasSize(1);
    }

    @Test
    public void findByLastName() {
        HttpInterceptor.setTenantId("0");
        List<Person> persons = personLogic.findByLastName("Simpson");
        assertThat(persons).isNotNull().hasSize(2);
        assertThat(persons.get(0).lastName()).isEqualTo("Simpson");

        HttpInterceptor.setTenantId("5a2f");
        assertThat(personLogic.findByLastName("Simpson")).isNotNull().hasSize(2);
    }
    @Test
    void save() {

        HttpInterceptor.setTenantId("4711");

        final Person person = personLogic.save(
            new Person(null,
                    "Homer",
                    "Simpson",
                    createAddress("Evergreen Terrace")
        ));

        assertThat(person).isNotNull();

    }

    private Address createAddress(String street) {
        return new Address(null,
                street, "Springfield " + HttpInterceptor.getTenantId());
    }
}