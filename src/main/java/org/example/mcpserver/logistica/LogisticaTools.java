package org.example.mcpserver.logistica;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.example.mcpserver.tooling.McpToolProvider;
import org.example.mcpserver.tooling.McpToolResultFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LogisticaTools implements McpToolProvider {

    private static final String TOOL_NAME = "logistica_consultar_envio";

    private static final Map<String, Object> INPUT_SCHEMA = Map.of(
            "type", "object",
            "properties", Map.of(
                    "envioId", Map.of(
                            "type", "string",
                            "description", "Identificador único del envío a consultar."
                    )
            ),
            "required", List.of("envioId"),
            "additionalProperties", false
    );

    private final LogisticaClient logisticaClient;
    private final McpToolResultFactory resultFactory;

    public LogisticaTools(LogisticaClient logisticaClient, McpToolResultFactory resultFactory) {
        this.logisticaClient = logisticaClient;
        this.resultFactory = resultFactory;
    }

    @Override
    public List<McpServerFeatures.SyncToolSpecification> toolSpecifications() {
        McpSchema.Tool tool = McpSchema.Tool.builder(TOOL_NAME, INPUT_SCHEMA)
                .description("Consultar estado de un envío en Logística. Usar cuando el usuario pida tracking o estado de entrega.")
                .build();

        return List.of(McpServerFeatures.SyncToolSpecification.builder()
                .tool(tool)
                .callHandler((exchange, request) -> {
                    Object envioId = request.arguments().get("envioId");
                    if (!(envioId instanceof String envioIdValue) || envioIdValue.isBlank()) {
                        return resultFactory.invalidArguments(TOOL_NAME, "envioId es obligatorio y debe ser texto no vacío");
                    }

                    try {
                        String response = logisticaClient.consultarEnvio(envioIdValue);
                        return resultFactory.success(response);
                    } catch (Exception ex) {
                        return resultFactory.fromFailure(TOOL_NAME, ex);
                    }
                })
                .build());
    }
}
