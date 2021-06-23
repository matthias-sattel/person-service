package org.goafabric.personservice.service;

import org.goafabric.personservice.client.PersonServiceClient;
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

    private PersonServiceClient personServiceClient;

    @PostConstruct
    private void init() {
        this.personServiceClient
                = new PersonServiceClient(restTemplateTest, "http://localhost"+ ":" + port);
    }

    @Test
    void getById() {
        //assertThat(personServiceClient.getById("1")).isNotNull();
    }

    @Test
    void findAll() {
        assertThat(personServiceClient.findAll())
                .isNotNull().isNotEmpty();
    }

    @Test
    void findByFirstName() {
        assertThat(personServiceClient.findByFirstName("Bart"))
                .isNotNull().hasSize(1);
    }

    @Test
    void findByLastName() {
        assertThat(personServiceClient.findByLastName("Simpson"))
                .isNotNull().hasSize(2);
    }
    
}