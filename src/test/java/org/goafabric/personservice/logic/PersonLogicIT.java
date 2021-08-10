package org.goafabric.personservice.logic;

import org.goafabric.personservice.crossfunctional.TenantIdInterceptor;
import org.goafabric.personservice.service.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonLogicIT {
    @Autowired
    private PersonLogic personLogic;

    @Test
    public void findById() {
        TenantIdInterceptor.setTenantId("0");
        List<Person> persons = personLogic.findAll();
        assertThat(persons).isNotNull().hasSize(3);

        final Person person
                = personLogic.getById(persons.get(0).getId());
        assertThat(person).isNotNull();
        assertThat(person.getFirstName()).isEqualTo(persons.get(0).getFirstName());
        assertThat(person.getLastName()).isEqualTo(persons.get(0).getLastName());

        TenantIdInterceptor.setTenantId("5a2f");
        assertThatThrownBy(() ->  personLogic.getById(persons.get(0).getId()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void findAll() {
        TenantIdInterceptor.setTenantId("0");
        assertThat(personLogic.findAll()).isNotNull().hasSize(3);

        TenantIdInterceptor.setTenantId("5a2f");
        assertThat(personLogic.findAll()).isNotNull().hasSize(0);
    }

    @Test
    public void findByFirstName() {
        TenantIdInterceptor.setTenantId("0");
        List<Person> persons = personLogic.findByFirstName("Monty");
        assertThat(persons).isNotNull().hasSize(1);
        assertThat(persons.get(0).getFirstName()).isEqualTo("Monty");
        assertThat(persons.get(0).getLastName()).isEqualTo("Burns");

        TenantIdInterceptor.setTenantId("5a2f");
        assertThat(personLogic.findByFirstName("Monty")).isNotNull().hasSize(0);
    }

    @Test
    public void findByLastName() {
        TenantIdInterceptor.setTenantId("0");
        List<Person> persons = personLogic.findByLastName("Simpson");
        assertThat(persons).isNotNull().hasSize(2);
        assertThat(persons.get(0).getLastName()).isEqualTo("Simpson");

        TenantIdInterceptor.setTenantId("5a2f");
        assertThat(personLogic.findByFirstName("Simpson")).isNotNull().hasSize(0);
    }
    @Test
    void save() {
        assertThat(personLogic.save(Person.builder()
                .build())).isNotNull();
    }
}