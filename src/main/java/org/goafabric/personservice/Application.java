package org.goafabric.personservice;

import io.micrometer.observation.ObservationRegistry;
import jakarta.servlet.DispatcherType;
import org.goafabric.personservice.adapter.Callee;
import org.goafabric.personservice.persistence.DatabaseProvisioning;
import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.observation.HttpRequestsObservationFilter;

import java.util.Arrays;


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
            //Logger and ExceptionHandler
            registerReflection(org.goafabric.personservice.crossfunctional.DurationLogger.class, hints);
            registerReflection(org.goafabric.personservice.crossfunctional.ExceptionHandler.class, hints);
            
            //REST and JBDC Pojos
            registerReflection(Callee.class, hints);
            registerReflection(org.goafabric.personservice.persistence.audit.AuditBean.AuditEvent.class, hints);

            //additional nod needed for boot 2.x
            registerReflection(SimpleClientHttpRequestFactory.class, hints);
            registerReflection(org.goafabric.personservice.persistence.multitenancy.TenantInspector.class, hints);
            hints.resources().registerResource(new ClassPathResource("db/migration/V1__init.sql"));
        }

        private void registerReflection(Class clazz, RuntimeHints hints) {
            Arrays.stream(clazz.getDeclaredConstructors()).forEach(
                    r -> hints.reflection().registerConstructor(r, ExecutableMode.INVOKE));
            Arrays.stream(clazz.getDeclaredMethods()).forEach(
                    r -> hints.reflection().registerMethod(r, ExecutableMode.INVOKE));
        }

    }

    @Bean
    FilterRegistrationBean traceWebFilter(ObservationRegistry observationRegistry) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new HttpRequestsObservationFilter(observationRegistry));
        filterRegistrationBean.setDispatcherTypes(DispatcherType.ASYNC, DispatcherType.ERROR, DispatcherType.FORWARD,
                DispatcherType.INCLUDE, DispatcherType.REQUEST);
        filterRegistrationBean.setOrder(Ordered.LOWEST_PRECEDENCE);
        return filterRegistrationBean;
    }

}
