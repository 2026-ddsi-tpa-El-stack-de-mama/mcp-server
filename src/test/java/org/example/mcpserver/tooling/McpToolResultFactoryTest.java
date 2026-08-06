package org.example.mcpserver.tooling;

import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class McpToolResultFactoryTest {

    private final McpToolResultFactory resultFactory = new McpToolResultFactory();

    @Test
    void generatesErrorResultForHttpStatusFailure() {
        McpSchema.CallToolResult result = resultFactory.fromFailure(
                "tool_test",
                HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "bad request", null, null, null)
        );

        assertTrue(result.isError());
    }

    @Test
    void generatesInvalidArgumentsResultAsError() {
        McpSchema.CallToolResult result = resultFactory.invalidArguments("tool_test", "faltan datos");

        assertTrue(result.isError());
    }
}
