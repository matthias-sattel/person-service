package org.goafabric.personservice.logic;

import org.goafabric.personservice.adapter.CalleeServiceAdapter;
import org.goafabric.personservice.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PersonLogicTest {
    private final PersonLogic personLogic = new PersonLogic(
            Mockito.mock(PersonMapper.class), Mockito.mock(PersonRepository.class), Mockito.mock(CalleeServiceAdapter.class));

    @Test
    void getById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findByFirstName() {
    }

    @Test
    void findByLastName() {
    }

    @Test
    void save() {
    }

    @Test
    void sayMyName() {
    }
}