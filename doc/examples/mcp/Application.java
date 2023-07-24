package org.goafabric.mcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * Created by amautsch on 26.06.2015.
 */

@SpringBootApplication
@ComponentScan({"org.goafabric.mcp",
                "org.goafabric.calleeservice.controller", "org.goafabric.calleeservice.logic",
                "org.goafabric.personservice.controller", "org.goafabric.personservice.logic", "org.goafabric.personservice.adapter",
})
@EnableJpaRepositories("org.goafabric.personservice.repository") @EntityScan("org.goafabric.personservice.repository")
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner init(ApplicationContext context, ReachDeepInsideTheCowsAss cow) {
        return args -> {
            if ((args.length > 0) && ("-check-integrity".equals(args[0]))) {SpringApplication.exit(context, () -> 0);}
            cow.run();
        };
    }


    @Component
    static class ReachDeepInsideTheCowsAss implements Runnable {
        @Autowired org.goafabric.calleeservice.logic.CalleeLogic calleeLogic;
        @Autowired org.goafabric.personservice.logic.PersonLogic personLogic;
        private final Logger log = LoggerFactory.getLogger(this.getClass());

        private PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);
        
        public void run() {
            log.info("reaching deep inside the cow's ass ....");

            //use simple observer pattern to decouple everything, so it still smells low cow shit, but at least does look fancy
            propertySupport.addPropertyChangeListener("callee",
                    evt -> log.info("" + calleeLogic.sayMyName(evt.getNewValue().toString())));
            propertySupport.addPropertyChangeListener("person",
                    evt -> log.info("" + personLogic.findByFirstName(evt.getNewValue().toString())));

            propertySupport.firePropertyChange("callee", "", "mooh");
            propertySupport.firePropertyChange("person", "", "Homer");

        }
    }

}
