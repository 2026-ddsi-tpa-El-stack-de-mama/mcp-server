package org.example.mcpserver.donadoresentidades;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class DonadoresEntidadesClient {

    private final RestClient restClient;

    public DonadoresEntidadesClient(@Qualifier("donacionesRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public String buscarPerfilDonador(String documento) {
        // TODO: mover a base URL dedicada si Donadores/Entidades se separa en otro microservicio.
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/donadores/{documento}").build(documento))
                .retrieve()
                .body(String.class);
    }
}
