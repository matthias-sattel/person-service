package org.goafabric.personservice.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatabaseProvisioning {
    @Value("${database.provisioning.goals:}")
    String goals;

    @Autowired
    PersonRepository personRepository;

    public void run() {
        if (goals.contains("-import-demo-data")) {
            log.info("Importing demo data");
            importDemoData();
        }
    }

    private void importDemoData() {
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
