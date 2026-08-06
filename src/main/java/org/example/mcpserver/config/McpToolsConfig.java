package org.example.mcpserver.config;

import org.example.mcpserver.tools.EjemploTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpToolsConfig {

  @Bean
  public ToolCallbackProvider ejemploToolCallbackProvider(EjemploTools ejemploTools) {
    return MethodToolCallbackProvider.builder()
        .toolObjects(ejemploTools)
        .build();
  }
}