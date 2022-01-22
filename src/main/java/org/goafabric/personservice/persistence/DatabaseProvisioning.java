package org.goafabric.personservice.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatabaseProvisioning {
    @Value("${database.provisioning.goals:}")
    String goals;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ApplicationContext applicationContext;

    public void run() {
        if (goals.contains("-import-demo-data")) {
            log.info("Importing demo data ...");
            importDemoData();
        }

        if (goals.contains("-terminate")) {
            log.info("Terminating app ...");
            SpringApplication.exit(applicationContext, () -> 0); //if an exception is raised, spring will automatically terminate with 1
        }
    }

    private void importDemoData() {
        if (personRepository.findAll().isEmpty()) {
            personRepository.save(PersonBo.builder()
                    .firstName("Homer").lastName("Simpson").secret("SuperSecret")
                    .build());

            personRepository.save(PersonBo.builder()
                    .firstName("Bart").lastName("Simpson").secret("SuperSecret")
                    .build());

            PersonBo person = personRepository.save(PersonBo.builder()
                    .firstName("Monty").lastName("Burns").secret("SuperSecret")
                    .build());

            person.setSecret("Secret");
            personRepository.save(person);
        }
    }
}
