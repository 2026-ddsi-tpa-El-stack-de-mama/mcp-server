package org.example.mcpserver.incentivos;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class IncentivosClient {

    private final RestClient restClient;

    public IncentivosClient(@Qualifier("incentivosRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public String consultarSaldoPuntos(String donadorId) {
        // TODO: validar endpoint final con el equipo de Incentivos.
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/incentivos/puntos/{donadorId}").build(donadorId))
                .retrieve()
                .body(String.class);
    }
}
