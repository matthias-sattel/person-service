package org.goafabric.personservice.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
//@Lazy //Lazy init, in case we need to reencrypt the password (cylic problem during startup)
public class CalleeServiceClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${adapter.calleeservice.url}")
    private String url;

    public Boolean isAlive() {
        return restTemplate.getForObject(url + "/callees/isAlive", Boolean.class);
    }
    
}
