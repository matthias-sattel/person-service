package org.goafabric.personservice.persistence;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.personservice.crossfunctional.HttpInterceptor;
import org.goafabric.personservice.persistence.domain.AddressBo;
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
                .address(createAddress("Evergreen Terrace 1"))
                .build());

        personRepository.save(PersonBo.builder()
                .firstName("Bart").lastName("Simpson")
                .address(createAddress("Everblue Terrace 1"))
                .build());

        personRepository.save(PersonBo.builder()
                .firstName("Monty").lastName("Burns")
                .address(createAddress("Monty Mansion"))
                .build());
    }

    private AddressBo createAddress(String street) {
        return AddressBo.builder()
                .street(street).city("Springfield " + HttpInterceptor.getTenantId())
                .build();
    }

}
