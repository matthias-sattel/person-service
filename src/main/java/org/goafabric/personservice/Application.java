package org.goafabric.personservice;

import org.goafabric.personservice.persistence.DatabaseProvisioning;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;


/**
 * Created by amautsch on 26.06.2015.
 */

@SpringBootApplication
@ImportRuntimeHints(Application.ApplicationRuntimeHints.class)
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner init(ApplicationContext context, DatabaseProvisioning databaseProvisioning) {
        return args -> {
            databaseProvisioning.run();
            if ((args.length > 0) && ("-check-integrity".equals(args[0]))) { SpringApplication.exit(context, () -> 0);}
        };

    }

    static class ApplicationRuntimeHints implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            //REST and JBDC Pojos
            hints.reflection().registerType(org.goafabric.personservice.adapter.Callee.class,
                    MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS);

            hints.reflection().registerType(org.goafabric.personservice.persistence.audit.AuditBean.AuditEvent.class,
                    MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS);

            //Persistence
            hints.reflection().registerType(org.goafabric.personservice.persistence.multitenancy.TenantInspector.class,
                    MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS);

            hints.resources().registerPattern("db/migration/*.sql");

            //openapi
            hints.reflection().registerType(java.lang.Module.class, MemberCategory.INVOKE_DECLARED_METHODS);
            hints.reflection().registerType(java.lang.ModuleLayer.class, MemberCategory.INVOKE_DECLARED_METHODS);
            hints.reflection().registerType(java.lang.module.Configuration.class, MemberCategory.INVOKE_DECLARED_METHODS);
            hints.reflection().registerType(java.lang.module.ResolvedModule.class, MemberCategory.INVOKE_DECLARED_METHODS);

            //org.springframework.cloud.client.circuitbreaker.observation.CircuitBreakerDocumentedObservation
        }
    }

}
