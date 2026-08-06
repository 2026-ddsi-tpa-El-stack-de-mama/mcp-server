package org.example.mcpserver.donaciones;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.example.mcpserver.tooling.McpToolProvider;
import org.example.mcpserver.tooling.McpToolResultFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DonacionesTools implements McpToolProvider {

    private static final String TOOL_NAME = "donaciones_registrar_donacion";

    private static final Map<String, Object> INPUT_SCHEMA = Map.of(
            "type", "object",
            "properties", Map.of(
                    "donadorId", Map.of(
                            "type", "string",
                            "description", "Identificador del donador que realiza la donación."
                    ),
                    "monto", Map.of(
                            "type", "number",
                            "minimum", 0,
                            "description", "Monto de la donación en moneda local."
                    )
            ),
            "required", List.of("donadorId", "monto"),
            "additionalProperties", false
    );

    private final DonacionesClient donacionesClient;
    private final McpToolResultFactory resultFactory;

    public DonacionesTools(DonacionesClient donacionesClient, McpToolResultFactory resultFactory) {
        this.donacionesClient = donacionesClient;
        this.resultFactory = resultFactory;
    }

    @Override
    public List<McpServerFeatures.SyncToolSpecification> toolSpecifications() {
        McpSchema.Tool tool = McpSchema.Tool.builder(TOOL_NAME, INPUT_SCHEMA)
                .description("Registrar una donación en el servicio de Donaciones. Usar cuando el usuario confirme monto y donador.")
                .build();

        return List.of(McpServerFeatures.SyncToolSpecification.builder()
                .tool(tool)
                .callHandler((exchange, request) -> {
                    Object donadorId = request.arguments().get("donadorId");
                    Object monto = request.arguments().get("monto");
                    if (!(donadorId instanceof String donadorIdValue) || donadorIdValue.isBlank()) {
                        return resultFactory.invalidArguments(TOOL_NAME, "donadorId es obligatorio y debe ser texto no vacío");
                    }
                    if (!(monto instanceof Number montoValue) || montoValue.doubleValue() < 0) {
                        return resultFactory.invalidArguments(TOOL_NAME, "monto debe ser numérico y mayor o igual a cero");
                    }

                    try {
                        String response = donacionesClient.registrarDonacion(donadorIdValue, montoValue.doubleValue());
                        return resultFactory.success(response);
                    } catch (Exception ex) {
                        return resultFactory.fromFailure(TOOL_NAME, ex);
                    }
                })
                .build());
    }
}
