package org.example.mcpserver.incentivos;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.example.mcpserver.tooling.McpToolProvider;
import org.example.mcpserver.tooling.McpToolResultFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IncentivosTools implements McpToolProvider {

    private static final String TOOL_NAME = "incentivos_consultar_puntos";

    private static final Map<String, Object> INPUT_SCHEMA = Map.of(
            "type", "object",
            "properties", Map.of(
                    "donadorId", Map.of(
                            "type", "string",
                            "description", "Identificador del donador para consultar su saldo de puntos."
                    )
            ),
            "required", List.of("donadorId"),
            "additionalProperties", false
    );

    private final IncentivosClient incentivosClient;
    private final McpToolResultFactory resultFactory;

    public IncentivosTools(IncentivosClient incentivosClient, McpToolResultFactory resultFactory) {
        this.incentivosClient = incentivosClient;
        this.resultFactory = resultFactory;
    }

    @Override
    public List<McpServerFeatures.SyncToolSpecification> toolSpecifications() {
        McpSchema.Tool tool = McpSchema.Tool.builder(TOOL_NAME, INPUT_SCHEMA)
                .description("Consultar saldo de puntos de incentivos para un donador. Usar cuando pidan beneficios o puntos acumulados.")
                .build();

        return List.of(McpServerFeatures.SyncToolSpecification.builder()
                .tool(tool)
                .callHandler((exchange, request) -> {
                    Object donadorId = request.arguments().get("donadorId");
                    if (!(donadorId instanceof String donadorIdValue) || donadorIdValue.isBlank()) {
                        return resultFactory.invalidArguments(TOOL_NAME, "donadorId es obligatorio y debe ser texto no vacío");
                    }

                    try {
                        String response = incentivosClient.consultarSaldoPuntos(donadorIdValue);
                        return resultFactory.success(response);
                    } catch (Exception ex) {
                        return resultFactory.fromFailure(TOOL_NAME, ex);
                    }
                })
                .build());
    }
}
