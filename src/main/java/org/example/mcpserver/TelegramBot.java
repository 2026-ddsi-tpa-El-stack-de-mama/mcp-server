package org.example.mcpserver;

import org.example.mcpserver.config.McpServerProperties;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Bot de Telegram = cliente MCP. Cada mensaje del usuario se le pasa al ChatClient
 * (Claude + tools del MCP via ToolCallbackProvider, ver ChatClientConfig); Spring AI
 * maneja el loop completo de tool-calling: si Claude decide invocar una tool, la
 * ejecuta contra el /mcp del propio server y le devuelve el resultado a Claude hasta
 * llegar a una respuesta final en texto.
 *
 * TODO: sin memoria de conversacion entre mensajes (cada uno es un prompt aislado).
 * Si en el futuro hace falta contexto multi-turno por chat, agregar un
 * ChatMemory keyeado por chatId.
 */
@Component
@Lazy
public class TelegramBot extends TelegramLongPollingBot {

    private final ChatClient chatClient;
    private final String botUsername;
    private final String botToken;

    public TelegramBot(ChatClient chatClient, McpServerProperties properties) {
        this.chatClient = chatClient;
        this.botUsername = properties.getTelegram().getBotUsername();
        this.botToken = properties.getTelegram().getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() == null || update.getMessage().getText() == null) {
            return;
        }
        String textoUsuario = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        String respuesta;
        try {
            respuesta = chatClient.prompt().user(textoUsuario).call().content();
        } catch (Exception e) {
            e.printStackTrace();
            respuesta = "Perdón, tuve un problema para responder.";
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(respuesta);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}