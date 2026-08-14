package org.example.mcpserver.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

  private static final String SYSTEM_PROMPT =
      """
      Sos el asistente de "El Stack de Mama", un sistema de gestion de donaciones para entidades
      beneficas. Tenes tools para consultar y operar sobre Logistica (depositos, stock, asignaciones),
      Donaciones (productos, categorias, donaciones), Donadores y Entidades (donadores, entidades
      beneficas, necesidades materiales) e Incentivos (misiones, insignias, categorias de donador).

      Respondes en español rioplatense, de forma clara y breve, como corresponde a un chat de Telegram.
      Si una operacion requiere un id que no tenes, pedilo antes de inventar uno. Si una tool devuelve
      un error, explicaselo al usuario en lenguaje simple, no le muestres el stacktrace.
      """;

  @Bean
  ChatClient chatClient(ChatClient.Builder builder, ToolCallbackProvider mcpTools) {
    return builder.defaultSystem(SYSTEM_PROMPT).defaultTools(mcpTools).build();
  }
}