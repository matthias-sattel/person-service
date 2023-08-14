package org.goafabric.personservice.logic;

import org.goafabric.personservice.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonLogicIT {
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void test() {

    }
}
