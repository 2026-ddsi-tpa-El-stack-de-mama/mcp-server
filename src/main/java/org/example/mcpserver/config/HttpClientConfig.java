package org.example.mcpserver.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class HttpClientConfig {

    @Bean
    @Qualifier("logisticaRestClient")
    RestClient logisticaRestClient(McpServerProperties properties) {
        return createRestClient(properties.getServices().getLogisticaBaseUrl(), properties);
    }

    @Bean
    @Qualifier("donacionesRestClient")
    RestClient donacionesRestClient(McpServerProperties properties) {
        return createRestClient(properties.getServices().getDonacionesBaseUrl(), properties);
    }

    @Bean
    @Qualifier("donadoresEntidadesRestClient")
    RestClient donadoresEntidadesRestClient(McpServerProperties properties) {
        return createRestClient(properties.getServices().getDonadoresEntidadesBaseUrl(), properties);
    }

    @Bean
    @Qualifier("incentivosRestClient")
    RestClient incentivosRestClient(McpServerProperties properties) {
        return createRestClient(properties.getServices().getIncentivosBaseUrl(), properties);
    }

    private RestClient createRestClient(String baseUrl, McpServerProperties properties) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Math.toIntExact(properties.getHttp().getConnectTimeout().toMillis()));
        requestFactory.setReadTimeout(Math.toIntExact(properties.getHttp().getReadTimeout().toMillis()));
        return RestClient.builder().baseUrl(baseUrl).requestFactory(requestFactory).build();
    }
}