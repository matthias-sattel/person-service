package org.goafabric.personservice;

import org.goafabric.personservice.persistence.DatabaseProvisioning;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.nativex.hint.AccessBits;
import org.springframework.nativex.hint.TypeHint;

/**
 * Created by amautsch on 26.06.2015.
 */

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})

@TypeHint(types = org.goafabric.personservice.adapter.Callee.class)

@TypeHint(types = {java.text.Normalizer.class, java.text.Normalizer.Form.class})
@TypeHint(types = org.jasypt.hibernate5.type.EncryptedStringType.class, typeNames = "encryptedString", access = AccessBits.ALL)
@TypeHint(typeNames = "org.goafabric.personservice.persistence.PersonBo.secret", access = AccessBits.ALL)

//@TypeHint(types = AuditJpaListener.class)
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner init(ApplicationContext context, DatabaseProvisioning databaseProvisioning) {
        return args -> {
            if ((args.length > 0) && ("-check-integrity".equals(args[0]))) { SpringApplication.exit(context, () -> 0);}
            else {
                databaseProvisioning.run();} //don't to stuff like this at home kidz
        };

    }
}
