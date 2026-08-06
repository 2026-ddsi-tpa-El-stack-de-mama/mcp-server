package org.example.mcpserver.donaciones;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class DonacionesClient {

    private final RestClient restClient;

    public DonacionesClient(@Qualifier("donacionesRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public String registrarDonacion(String donadorId, Double monto) {
        // TODO: definir endpoint y payload final de donaciones.
        return restClient.post()
                .uri("/api/donaciones")
                .body(new RegistrarDonacionRequest(donadorId, monto))
                .retrieve()
                .body(String.class);
    }

    private record RegistrarDonacionRequest(String donadorId, Double monto) {
    }
}
