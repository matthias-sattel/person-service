package org.goafabric.personservice.logic;

import org.goafabric.personservice.service.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonLogicIT {
    @Autowired
    private PersonLogic personLogic;

    @Test
    void getById() {
        assertThat(personLogic.getById("1")).isNotNull();
    }

    @Test
    void findAll() {
        assertThat(personLogic.findAll()).isNotNull();
    }

    @Test
    void findByFirstName() {
        assertThat(personLogic.findByFirstName("")).isNotNull();
    }

    @Test
    void findByLastName() {
        assertThat(personLogic.findByLastName("")).isNotNull();
    }

    @Test
    void save() {
        assertThat(personLogic.save(new Person())).isNotNull();
    }
}