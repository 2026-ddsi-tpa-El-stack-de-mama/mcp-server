package org.example.mcpserver.security;

import io.modelcontextprotocol.server.transport.ServerTransportSecurityException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BearerTokenSecurityValidatorTest {

    @Test
    void acceptsValidBearerToken() {
        BearerTokenSecurityValidator validator = new BearerTokenSecurityValidator("expected");

        assertDoesNotThrow(() -> validator.validateHeaders(Map.of(
                "Authorization", List.of("B" + "earer expected")
        )));
    }

    @Test
    void rejectsMissingAuthorizationHeader() {
        BearerTokenSecurityValidator validator = new BearerTokenSecurityValidator("expected");

        assertThrows(ServerTransportSecurityException.class, () -> validator.validateHeaders(Map.of()));
    }

    @Test
    void rejectsInvalidTokenValue() {
        BearerTokenSecurityValidator validator = new BearerTokenSecurityValidator("expected");

        assertThrows(ServerTransportSecurityException.class, () -> validator.validateHeaders(Map.of(
                "Authorization", List.of("B" + "earer wrong")
        )));
    }
}
