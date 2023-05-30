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
    public CalleeServiceAdapter calleeServiceAdapter(
            @Value("${adapter.calleeservice.url}") String url, @Value("${adapter.timeout}") Long timeout, @Value("${adapter.maxlifetime:-1}") Long maxLifeTime) {
        return createAdapter(CalleeServiceAdapter.class, builder(), url, timeout, maxLifeTime);
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder builder() {
        return WebClient.builder();
    }

    public static <A> A createAdapter(Class<A> adapterType, WebClient.Builder builder, String url, Long timeout, Long maxLifeTime) {
        var client = builder
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create(
                        ConnectionProvider.builder("custom").maxLifeTime(Duration.ofMillis(maxLifeTime)).build())))
                .baseUrl(url).defaultHeaders(header -> header.setBasicAuth("admin", "admin")).build();
        return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).blockTimeout(Duration.ofMillis(timeout))
                .build().createClient(adapterType);
    }
}


