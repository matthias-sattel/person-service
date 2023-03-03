package org.goafabric.personservice.adapter;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RegisterReflectionForBinding(Callee.class)
@CircuitBreaker(name = "CalleeService")
public class CalleeServiceAdapter {
    private final RestTemplate restTemplate;

    private final String url;

    public CalleeServiceAdapter(RestTemplate restTemplate, @Value("${adapter.calleeservice.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public Callee sayMyName(String name) {
        return restTemplate.getForObject(url + "/callees/sayMyName?name={name}", Callee.class, name);
    }
}
