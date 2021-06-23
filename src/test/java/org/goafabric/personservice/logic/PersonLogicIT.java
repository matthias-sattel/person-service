package org.goafabric.personservice.logic;

import org.goafabric.personservice.service.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonLogicIT {
    @Autowired
    private PersonLogic personLogic;

    @Test
    void getById() {
        //assertThat(personLogic.getById("1")).isNotNull();
    }

    @Test
    void findAll() {
        assertThat(personLogic.findAll())
                .isNotNull();
    }

    @Test
    void findByFirstName() {
        assertThat(personLogic.findByFirstName("Bart"))
                .isNotNull().hasSize(1);
    }

    @Test
    void findByLastName() {
        assertThat(personLogic.findByLastName("Simpson"))
                .isNotNull().hasSize(2);
    }

    @Test
    void save() {
        assertThat(personLogic.save(Person.builder()
                .build())).isNotNull();
    }
}