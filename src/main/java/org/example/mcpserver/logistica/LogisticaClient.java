package org.example.mcpserver.logistica;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class LogisticaClient {

    private final RestClient restClient;

    public LogisticaClient(@Qualifier("logisticaRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public String consultarEnvio(String envioId) {
        // TODO: validar contrato final del endpoint con el equipo de Logística.
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/logistica/envios/{id}").build(envioId))
                .retrieve()
                .body(String.class);
    }
}
