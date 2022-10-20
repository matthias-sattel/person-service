package org.goafabric.personservice.adapter;

import lombok.SneakyThrows;
import org.goafabric.personservice.crossfunctional.HttpInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;
import java.util.Base64;
import java.util.Collections;

@Configuration
public class CalleeServiceConfiguration {

    @Bean
    public RestTemplate restTemplate(
            @Value("${adapter.calleeservice.user}") String user,
            @Value("${adapter.calleeservice.password}") String password,
            @Value("${adapter.timeout}") Integer timeout) {
        final RestTemplate restTemplate = new RestTemplateBuilder()
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

    
    @Bean
    @SneakyThrows
    CalleeServiceAdapterDec calleeServiceAdapterDec(
            @Value("${adapter.calleeservice.url}") String url,
            @Value("${adapter.calleeservice.user}") String user,
            @Value("${adapter.calleeservice.password}") String password,
            @Value("${adapter.timeout}") Integer timeout) {

        final HttpServiceProxyFactory proxyFactory = WebClientAdapter.createHttpServiceProxyFactory(
            WebClient.builder()
                    .baseUrl(url)
                    .defaultHeaders(header -> header.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                    .defaultHeaders(header -> header.setBasicAuth(new String(Base64.getDecoder().decode(user)), new String(Base64.getDecoder().decode(password))))
                    .defaultHeaders(header -> header.set("X-TenantId", HttpInterceptor.getTenantId()))
                    .defaultHeaders(header -> header.set("X-Auth-Request-Preferred-Username", HttpInterceptor.getUserName()))
                    .build()
        );

        proxyFactory.setBlockTimeout(Duration.ofSeconds(timeout));
        proxyFactory.afterPropertiesSet();

        return proxyFactory
                .createClient(CalleeServiceAdapterDec.class);
    }

}
