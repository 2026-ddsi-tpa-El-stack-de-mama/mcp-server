package org.example.mcpserver.donadoresentidades;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.example.mcpserver.tooling.McpToolProvider;
import org.example.mcpserver.tooling.McpToolResultFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DonadoresEntidadesTools implements McpToolProvider {

    private static final String TOOL_NAME = "donadores_entidades_buscar_donador";

    private static final Map<String, Object> INPUT_SCHEMA = Map.of(
            "type", "object",
            "properties", Map.of(
                    "documento", Map.of(
                            "type", "string",
                            "description", "Documento del donador (DNI/RUC u otro identificador funcional)."
                    )
            ),
            "required", List.of("documento"),
            "additionalProperties", false
    );

    private final DonadoresEntidadesClient donadoresEntidadesClient;
    private final McpToolResultFactory resultFactory;

    public DonadoresEntidadesTools(DonadoresEntidadesClient donadoresEntidadesClient, McpToolResultFactory resultFactory) {
        this.donadoresEntidadesClient = donadoresEntidadesClient;
        this.resultFactory = resultFactory;
    }

    @Override
    public List<McpServerFeatures.SyncToolSpecification> toolSpecifications() {
        McpSchema.Tool tool = McpSchema.Tool.builder(TOOL_NAME, INPUT_SCHEMA)
                .description("Buscar información de donador/entidad. Usar cuando necesites validar o recuperar el perfil base del donador.")
                .build();

        return List.of(McpServerFeatures.SyncToolSpecification.builder()
                .tool(tool)
                .callHandler((exchange, request) -> {
                    Object documento = request.arguments().get("documento");
                    if (!(documento instanceof String documentoValue) || documentoValue.isBlank()) {
                        return resultFactory.invalidArguments(TOOL_NAME, "documento es obligatorio y debe ser texto no vacío");
                    }

                    try {
                        String response = donadoresEntidadesClient.buscarPerfilDonador(documentoValue);
                        return resultFactory.success(response);
                    } catch (Exception ex) {
                        return resultFactory.fromFailure(TOOL_NAME, ex);
                    }
                })
                .build());
    }
}
