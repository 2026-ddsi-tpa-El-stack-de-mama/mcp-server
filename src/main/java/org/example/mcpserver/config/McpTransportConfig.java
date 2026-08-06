package org.example.mcpserver.config;

import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletStreamableServerTransportProvider;
import io.modelcontextprotocol.server.transport.ServerTransportSecurityValidator;
import io.modelcontextprotocol.spec.McpSchema;
import org.example.mcpserver.security.BearerTokenSecurityValidator;
import org.example.mcpserver.tooling.McpToolProvider;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class McpTransportConfig {

    @Bean
    ServerTransportSecurityValidator serverTransportSecurityValidator(McpServerProperties properties) {
        return new BearerTokenSecurityValidator(properties.getSecurity().getBearerToken());
    }

    @Bean
    HttpServletStreamableServerTransportProvider streamableTransportProvider(
            McpServerProperties properties,
            ServerTransportSecurityValidator securityValidator
    ) {
        return HttpServletStreamableServerTransportProvider.builder()
                .mcpEndpoint(properties.getMcp().getEndpoint())
                .securityValidator(securityValidator)
                .build();
    }

    @Bean
    ServletRegistrationBean<HttpServletStreamableServerTransportProvider> mcpServletRegistration(
            HttpServletStreamableServerTransportProvider streamableTransportProvider
    ) {
        return new ServletRegistrationBean<>(streamableTransportProvider, "/*");
    }

    @Bean(destroyMethod = "close")
    McpSyncServer mcpSyncServer(
            HttpServletStreamableServerTransportProvider streamableTransportProvider,
            List<McpToolProvider> toolProviders
    ) {
        McpSyncServer server = McpServer.sync(streamableTransportProvider)
                .serverInfo("mcp-server", "0.1.0")
                .capabilities(McpSchema.ServerCapabilities.builder().tools(true).build())
                .build();

        toolProviders.stream()
                .map(McpToolProvider::toolSpecifications)
                .flatMap(List::stream)
                .forEach(server::addTool);

        return server;
    }
}
