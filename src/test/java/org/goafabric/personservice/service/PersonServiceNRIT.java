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

    private PersonServiceAdapter personServiceAdapter;

    @PostConstruct
    private void init() {
        this.personServiceAdapter
                = new PersonServiceAdapter(restTemplateTest, "http://localhost"+ ":" + port);
    }

    @Test
    void getById() {
        //assertThat(personServiceClient.getById("1")).isNotNull();
    }

    @Test
    void findAll() {
        assertThat(personServiceAdapter.findAll())
                .isNotNull().isNotEmpty();
    }

    @Test
    void findByFirstName() {
        assertThat(personServiceAdapter.findByFirstName("Bart"))
                .isNotNull().hasSize(1);
    }

    @Test
    void findByLastName() {
        assertThat(personServiceAdapter.findByLastName("Simpson"))
                .isNotNull().hasSize(2);
    }
    
}