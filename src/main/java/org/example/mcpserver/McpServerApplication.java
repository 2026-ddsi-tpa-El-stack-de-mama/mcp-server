package org.example.mcpserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@ConfigurationPropertiesScan
public class McpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }

    /**
     * Registra el TelegramBot (bean de Spring, con el ChatClient + tools MCP ya inyectados)
     * una vez que el contexto termino de levantar. En Spring Boot, el servidor web embebido
     * ya esta escuchando en este punto (se inicia durante el refresh del ApplicationContext,
     * antes de que corran los CommandLineRunner), asi que el propio endpoint /mcp ya deberia
     * estar disponible para cuando el TelegramBot reciba el primer mensaje.
     */
    @Bean
    CommandLineRunner registrarBotDeTelegram(TelegramBot telegramBot) {
        return args -> {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            try {
                botsApi.registerBot(telegramBot);
                System.out.println("Bot de Telegram iniciado correctamente");
            } catch (TelegramApiException e) {
                throw new RuntimeException("No se pudo registrar el bot de Telegram", e);
            }
        };
    }
}