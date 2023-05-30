package org.goafabric.personservice.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class CalleeServiceAdapterConfiguration {
    @Bean
    @LoadBalanced
    public WebClient.Builder builder() {
        return WebClient.builder();
    }

    @Bean
    public CalleeServiceAdapter calleeServiceAdapter(WebClient.Builder builder,
                                                     @Value("${adapter.calleeservice.url}") String url, @Value("${adapter.timeout}") Long timeout, @Value("${adapter.maxlifetime:-1}") Long maxLifeTime) {

        var client = builder()
                .clientConnector(getConnector(maxLifeTime))
                .baseUrl(url).defaultHeaders(header -> header.setBasicAuth("admin", "admin")).build();
        return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).blockTimeout(Duration.ofMillis(timeout))
                .build().createClient(CalleeServiceAdapter.class);
    }

    private ReactorClientHttpConnector getConnector(Long maxLifeTime) {
        return new ReactorClientHttpConnector(HttpClient.create(
                ConnectionProvider.builder("custom").maxLifeTime(Duration.ofMillis(maxLifeTime)).build()));
    }
}


