package org.goafabric.personservice.repository.extensions;

import org.goafabric.personservice.controller.vo.Address;
import org.goafabric.personservice.controller.vo.Person;
import org.goafabric.personservice.extensions.HttpInterceptor;
import org.goafabric.personservice.logic.PersonLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DemoDataImporter implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String goals;

    private final String tenants;

    private final ApplicationContext applicationContext;

    public DemoDataImporter(@Value("${database.provisioning.goals:}")String goals, @Value("${multi-tenancy.tenants}") String tenants,
                            ApplicationContext applicationContext) {
        this.goals = goals;
        this.tenants = tenants;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) {
        if ((args.length > 0) && ("-check-integrity".equals(args[0]))) { return; }

        if (goals.contains("-import-demo-data")) {
            log.info("Importing demo data ...");
            importDemoData();
            log.info("Demo data import done ...");
        }

        if (goals.contains("-terminate")) {
            log.info("Terminating app ...");
            SpringApplication.exit(applicationContext, () -> 0); //if an exception is raised, spring will automatically terminate with 1
        }
    }

    private void importDemoData() {
        Arrays.asList(tenants.split(",")).forEach(tenant -> {
            HttpInterceptor.setTenantId(tenant);
            if (applicationContext.getBean(PersonLogic.class).findAll().isEmpty()) {
                insertData();
            }
        });
        HttpInterceptor.setTenantId("0");
    }

    private void insertData() {
        applicationContext.getBean(PersonLogic.class).save(new Person(null, null, "Homer", "Simpson"
                        , List.of(createAddress("Evergreen Terrace 1"))));

        applicationContext.getBean(PersonLogic.class).save(new Person(null, null, "Bart", "Simpson"
                , List.of(createAddress("Everblue Terrace 1"))));

        applicationContext.getBean(PersonLogic.class).save(new Person(null, null, "Monty", "Burns"
                , List.of(createAddress("Monty Mansion"))));

    }

    private Address createAddress(String street) {
        return new Address(null, null, street, "Springfield " + HttpInterceptor.getTenantId());
    }

}
