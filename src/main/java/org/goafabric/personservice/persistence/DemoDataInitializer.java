package org.goafabric.personservice.persistence;

import org.goafabric.personservice.logic.PersonLogic;
import org.goafabric.personservice.service.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemoDataInitializer {
    @Autowired
    private PersonLogic personLogic;

    public void run() {
        if (personLogic.findAll().size() == 0) {
            personLogic.save(Person.builder()
                    .firstName("Homer").lastName("Simpson")
                    .build());

            personLogic.save(Person.builder()
                    .firstName("Bart").lastName("Simpson")
                    .build());

            personLogic.save(Person.builder()
                    .firstName("Monty").lastName("Burns")
                    .build());
        }
    }
}
