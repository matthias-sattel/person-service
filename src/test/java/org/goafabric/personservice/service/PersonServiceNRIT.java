package org.goafabric.personservice.service;

import org.goafabric.personservice.client.PersonServiceAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonServiceNRIT {
    @Autowired
    private RestTemplate restTemplateTest;

    @LocalServerPort
    private int port;

    private PersonServiceAdapter personService;

    @PostConstruct
    private void init() {
        this.personService
                = new PersonServiceAdapter(restTemplateTest, "http://localhost"+ ":" + port);
    }

    @Test
    void getById() {
        //assertThat(personServiceClient.getById("1")).isNotNull();
    }

    @Test
    void findAll() {
        assertThat(personService.findAll())
                .isNotNull().isNotEmpty();
    }

    @Test
    void findByFirstName() {
        assertThat(personService.findByFirstName("Bart"))
                .isNotNull().hasSize(1);
    }

    @Test
    void findByLastName() {
        assertThat(personService.findByLastName("Simpson"))
                .isNotNull().hasSize(2);
    }
    
}