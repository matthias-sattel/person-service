package org.goafabric.personservice.persistence;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.personservice.crossfunctional.HttpInterceptor;
import org.goafabric.personservice.persistence.domain.PersonBo;
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
            HttpInterceptor.setTenantId("0");
            insertData();
            HttpInterceptor.setTenantId("5a2f");
            insertData();
        }
    }

    private void insertData() {
        personRepository.save(PersonBo.builder()
                .firstName("Homer").lastName("Simpson")
                .build());

        personRepository.save(PersonBo.builder()
                .firstName("Bart").lastName("Simpson")
                .build());

        PersonBo person = personRepository.save(PersonBo.builder()
                .firstName("Monty").lastName("Burns")
                .build());
        
        personRepository.save(person);
    }
}
