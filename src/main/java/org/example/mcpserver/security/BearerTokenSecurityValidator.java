package org.example.mcpserver.security;

import io.modelcontextprotocol.server.transport.ServerTransportSecurityException;
import io.modelcontextprotocol.server.transport.ServerTransportSecurityValidator;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BearerTokenSecurityValidator implements ServerTransportSecurityValidator {

    private final String expectedBearerToken;

    public BearerTokenSecurityValidator(String expectedBearerToken) {
        this.expectedBearerToken = Objects.requireNonNull(expectedBearerToken, "expectedBearerToken must not be null");
    }

    @Override
    public void validateHeaders(Map<String, List<String>> headers) throws ServerTransportSecurityException {
        String authorizationHeader = headers.entrySet().stream()
                .filter(entry -> "authorization".equalsIgnoreCase(entry.getKey()))
                .flatMap(entry -> entry.getValue().stream())
                .findFirst()
                .orElseThrow(() -> new ServerTransportSecurityException(401, "Missing Authorization header"));

        String prefix = "Bearer ";
        if (!authorizationHeader.startsWith(prefix)) {
            throw new ServerTransportSecurityException(401, "Invalid Authorization scheme");
        }

        String providedToken = authorizationHeader.substring(prefix.length()).trim();
        if (!expectedBearerToken.equals(providedToken)) {
            throw new ServerTransportSecurityException(403, "Invalid bearer token");
        }
    }
}
