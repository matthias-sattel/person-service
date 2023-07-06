package org.goafabric.personservice.repository.extensions;

import org.goafabric.personservice.extensions.HttpInterceptor;
import org.goafabric.personservice.controller.vo.Address;
import org.goafabric.personservice.controller.vo.Person;
import org.goafabric.personservice.logic.PersonLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseProvisioning implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String goals;

    private final String tenants;

    private final ApplicationContext applicationContext;

    public DatabaseProvisioning(@Value("${database.provisioning.goals:}")String goals, @Value("${multi-tenancy.tenants}") String tenants,
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
    }

    private void insertData() {
        applicationContext.getBean(PersonLogic.class).save(new Person(null, "Homer", "Simpson"
                        , createAddress("Evergreen Terrace 1")));

        applicationContext.getBean(PersonLogic.class).save(new Person(null, "Bart", "Simpson"
                , createAddress("Everblue Terrace 1")));

        applicationContext.getBean(PersonLogic.class).save(new Person(null, "Monty", "Burns"
                , createAddress("Monty Mansion")));

    }

    private Address createAddress(String street) {
        return new Address(null, "street", "Springfield " + HttpInterceptor.getTenantId());
    }

}
