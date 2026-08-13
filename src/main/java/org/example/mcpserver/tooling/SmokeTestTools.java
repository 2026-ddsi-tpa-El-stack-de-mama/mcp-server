package org.example.mcpserver.tooling;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

@Component
public class SmokeTestTools {

  @McpTool(name = "saludar", description = "Verifica que el servidor MCP esta funcionando")
  public String saludar(@McpToolParam(description = "Nombre de la persona", required = true) String nombre) {
    return "Hola " + nombre + ", el servidor MCP de El Stack de Mamá esta funcionando correctamente.";
  }
}