package org.goafabric.personservice.repository;

import org.goafabric.personservice.extensions.HttpInterceptor;
import org.goafabric.personservice.repository.entity.AddressEo;
import org.goafabric.personservice.repository.entity.PersonEo;
import org.goafabric.personservice.repository.extensions.AuditrepositoryAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuditReposityAspectIT {
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void createAndDelete() {
        AuditrepositoryAspect.interactions.clear();

        var person = createPerson("Homer", "Simpson", "Ervergreen Terrace");
        personRepository.save(person);
        personRepository.deleteById(person.id);
        personRepository.delete(person);

        assertThat(AuditrepositoryAspect.interactions.get("create")).isEqualTo(1);
        assertThat(AuditrepositoryAspect.interactions.get("delete")).isEqualTo(2);
    }

    @Test
    public void createAllAndDelete() {
        AuditrepositoryAspect.interactions.clear();

        var persons = Arrays.asList(
                createPerson("Homer", "Simpson", "Ervergreen Terrace"),
                createPerson("Bart", "Simpson", "Ervergreen Terrace"));

        personRepository.saveAll(persons);
        personRepository.deleteAll(persons);

        assertThat(AuditrepositoryAspect.interactions.get("create")).isEqualTo(2);
        assertThat(AuditrepositoryAspect.interactions.get("delete")).isEqualTo(2);
    }
    
    @Test
    public void update() {
        var person = createPerson("Homer", "Simpson", "Ervergreen Terrace");
        personRepository.save(person);

        person.firstName = "Bart";
        personRepository.save(person);
    }

    private PersonEo createPerson(String firstName, String lastName, String street) {
        var person = new PersonEo();
        person.firstName = firstName;
        person.lastName = lastName;
        person.address = Collections.singletonList(createAddress(street));
        return person;
    }
    private AddressEo createAddress(String street) {
        var address = new AddressEo();
        address.street = street;
        address.city = "Springfield " + HttpInterceptor.getTenantId();
        return address;
    }

}