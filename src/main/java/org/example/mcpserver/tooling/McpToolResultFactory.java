package org.example.mcpserver.tooling;

import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.net.http.HttpTimeoutException;
import java.util.List;

@Component
public class McpToolResultFactory {

    public McpSchema.CallToolResult success(String payload) {
        return McpSchema.CallToolResult.builder()
                .content(List.of(new McpSchema.TextContent(payload)))
                .build();
    }

    public McpSchema.CallToolResult fromFailure(String toolName, Exception exception) {
        String message = "Error inesperado al ejecutar la tool '" + toolName + "'.";

        if (exception instanceof RestClientResponseException restException) {
            message = "La tool '" + toolName + "' recibió HTTP " + restException.getStatusCode().value()
                    + " desde el microservicio remoto.";
        } else if (exception instanceof ResourceAccessException
                || exception.getCause() instanceof HttpTimeoutException) {
            message = "Timeout o falta de respuesta del microservicio al ejecutar la tool '" + toolName + "'.";
        }

        return McpSchema.CallToolResult.builder()
                .isError(true)
                .content(List.of(new McpSchema.TextContent(message)))
                .build();
    }

    public McpSchema.CallToolResult invalidArguments(String toolName, String reason) {
        return McpSchema.CallToolResult.builder()
                .isError(true)
                .content(List.of(new McpSchema.TextContent("Argumentos inválidos para '" + toolName + "': " + reason)))
                .build();
    }
}
