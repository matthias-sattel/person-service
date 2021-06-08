package org.goafabric.personservice.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class CalleeServiceAdapter {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${adapter.calleeservice.url}")
    private String url;

    public Boolean isAlive() {
        log.info("Calling CalleService ...");
        final Boolean isAlive = restTemplate.getForObject(url + "/callees/isAlive", Boolean.class);
        log.info("got: " + isAlive);
        return isAlive;
    }
}
