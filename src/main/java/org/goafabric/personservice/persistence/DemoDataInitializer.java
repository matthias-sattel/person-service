package org.goafabric.personservice.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemoDataInitializer {
    @Autowired
    private PersonRepository personRepository;

    public void run() {
        if (personRepository.findAll().isEmpty()) {
            personRepository.save(PersonBo.builder()
                    .firstName("Homer").lastName("Simpson")
                    .build());

            personRepository.save(PersonBo.builder()
                    .firstName("Bart").lastName("Simpson")
                    .build());

            personRepository.save(PersonBo.builder()
                    .firstName("Monty").lastName("Burns")
                    .build());
        }
    }
}
