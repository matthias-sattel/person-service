package org.goafabric.personservice.controller;

import org.goafabric.personservice.logic.PersonLogic;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PersonControllerTest {
    private PersonLogic personLogic = Mockito.mock(PersonLogic.class);
    private PersonController personController = new PersonController(personLogic);

    @Test
    void getById() {
        assertThat(personController.getById("0")).isNull();
    }

    @Test
    void findAll() {
        assertThat(personController.findAll()).isNotNull();
    }

    @Test
    void findByFirstName() {
        assertThat(personController.findByFirstName("Homer")).isNotNull();
    }

    @Test
    void findByLastName() {
        assertThat(personController.findByLastName("Homer")).isNotNull();
    }

    @Test
    void save() {
        assertThat(personController.save(null)).isNull();
        verify(personLogic, times(1)).save(null);
    }

    @Test
    void sayMyName() {
        assertThat(personController.sayMyName("Heisenberg")).isNull();
    }
}