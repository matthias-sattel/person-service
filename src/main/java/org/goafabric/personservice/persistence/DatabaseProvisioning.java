package org.goafabric.personservice.persistence;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.personservice.controller.dto.Address;
import org.goafabric.personservice.controller.dto.Person;
import org.goafabric.personservice.crossfunctional.HttpInterceptor;
import org.goafabric.personservice.logic.PersonLogic;
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
    PersonLogic personRepository;

    @Autowired
    ApplicationContext applicationContext;

    public void run() {
        if (goals.contains("-import-demo-data")) {
            log.info("Importing demo data ...");
            importDemoData();
            log.info("Demodata import done ...");
        }

        if (goals.contains("-terminate")) {
            log.info("Terminating app ...");
            SpringApplication.exit(applicationContext, () -> 0); //if an exception is raised, spring will automatically terminate with 1
        }
    }

    private void importDemoData() {
        HttpInterceptor.setTenantId("0");
        if (personRepository.findAll().isEmpty()) {
            HttpInterceptor.setTenantId("0");
            insertData();
            HttpInterceptor.setTenantId("5a2f");
            insertData();
        }
    }

    private void insertData() {
        personRepository.save(Person.builder()
                .firstName("Homer").lastName("Simpson")
                .address(createAddress("Evergreen Terrace 1"))
                .build());

        personRepository.save(Person.builder()
                .firstName("Bart").lastName("Simpson")
                .address(createAddress("Everblue Terrace 1"))
                .build());

        personRepository.save(Person.builder()
                .firstName("Monty").lastName("Burns")
                .address(createAddress("Monty Mansion"))
                .build());
    }

    private Address createAddress(String street) {
        return Address.builder()
                .street(street).city("Springfield " + HttpInterceptor.getTenantId())
                .build();
    }

}
