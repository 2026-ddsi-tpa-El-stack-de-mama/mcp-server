package org.example.mcpserver.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class EjemploTools {

  @Tool(description = "Devuelve un saludo de prueba para verificar que el servidor MCP está " +
      "correctamente conectado y puede ser invocado desde Claude.")
  public String saludar(
      @ToolParam(description = "Nombre de la persona a saludar", required = true)
      String nombre
  ) {
    return "Hola " + nombre + ", el servidor MCP de El Stack de Mamá está funcionando correctamente.";
  }
}