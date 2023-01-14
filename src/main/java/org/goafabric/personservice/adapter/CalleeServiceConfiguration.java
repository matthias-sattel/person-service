package org.goafabric.personservice.adapter;

import org.goafabric.personservice.crossfunctional.HttpInterceptor;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Base64;
import java.util.Collections;

@Configuration
@ImportRuntimeHints(CalleeServiceConfiguration.ApplicationRuntimeHints.class)
public class CalleeServiceConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder,
            @Value("${adapter.calleeservice.user}") String user,
            @Value("${adapter.calleeservice.password}") String password,
            @Value("${adapter.timeout}") Integer timeout) {
        RestTemplate restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(timeout))
                .setReadTimeout(Duration.ofMillis(timeout))
                .build();

        restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            request.getHeaders().setBasicAuth(new String(Base64.getDecoder().decode(user)), new String(Base64.getDecoder().decode(password)));
            request.getHeaders().set("X-TenantId", HttpInterceptor.getTenantId());
            request.getHeaders().set("X-Auth-Request-Preferred-Username", HttpInterceptor.getUserName());
            return execution.execute(request, body);
        });
        return restTemplate;
    }

    static class ApplicationRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.reflection().registerType(io.github.resilience4j.spring6.circuitbreaker.configure.CircuitBreakerAspect.class,
                    builder -> builder.withMembers(MemberCategory.INVOKE_DECLARED_METHODS));
        }
    }

}
