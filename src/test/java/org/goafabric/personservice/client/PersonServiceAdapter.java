package org.goafabric.personservice.client;

import org.goafabric.personservice.service.Person;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class PersonServiceAdapter {
    private final RestTemplate restTemplate;

    private final String serviceUrl;

    public PersonServiceAdapter(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.serviceUrl = baseUrl + "/persons";
    }

    public Person getById(String id) {
        return restTemplate.getForObject(serviceUrl + "/getById/?id={id}",
                Person.class, id);
    }

    public List<Person> findAll() {
        return restTemplate.getForObject(serviceUrl + "/findAll",
                List.class);
    }

    public List<Person> findByFirstName(String firstName) {
        return restTemplate.getForObject(serviceUrl + "/findByFirstName?firstName={firstName}",
                List.class, firstName);
    }

    public List<Person>findByLastName(String lastName) {
        return restTemplate.getForObject(serviceUrl + "/findByLastName?lastName={lastName}",
                List.class, lastName);
    }

    public Boolean isAlive() {
        return restTemplate.getForObject(serviceUrl + "/isAlive", Boolean.class);
    }

}
