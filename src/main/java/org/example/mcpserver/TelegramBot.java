package org.example.mcpserver;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TelegramBot extends TelegramLongPollingBot {
    private final AnthropicClient claudeClient = AnthropicOkHttpClient.fromEnv();

    @Override
    public void onUpdateReceived(Update update) {
// Esta función se invocará cuando nuestro bot reciba un mensaje
// Se obtiene el mensaje escrito por el usuario
        final String messageTextReceived = update.getMessage().getText();

        String respuestaClaude = preguntarAClaude(messageTextReceived);

        // Se obtiene el id de chat del usuario
        Long chatId = update.getMessage().getChatId();
// Se crea un objeto mensaje
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(respuestaClaude);
        try {
// Se envía el mensaje
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String preguntarAClaude(String textoUsuario) {
        try {
            String apiKey = System.getenv("ANTHROPIC_API_KEY");

            JSONObject mensaje = new JSONObject();
            mensaje.put("role", "user");
            mensaje.put("content", textoUsuario);

            JSONObject body = new JSONObject();
            body.put("model", "claude-sonnet-5");
            body.put("max_tokens", 1000);
            body.put("messages", new JSONArray().put(mensaje));

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.anthropic.com/v1/messages"))
                    .header("x-api-key", apiKey)
                    .header("anthropic-version", "2023-06-01")
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());

            if (json.has("error")) {
                System.err.println("Error de Claude: " + json.getJSONObject("error").getString("message"));
                return "Perdón, tuve un problema para responder.";
            }

            return json.getJSONArray("content")
                    .getJSONObject(0)
                    .getString("text");

        } catch (Exception e) {
            e.printStackTrace();
            return "Perdón, tuve un problema para responder.";
        }
    }


    @Override
    public String getBotUsername() {
// Se devuelve el nombre que dimos al bot al crearlo con el BotFather
        return "Grupo8_DSI_bot";
    }
    @Override
    public String getBotToken() {
// Se devuelve el token que nos generó el BotFather de nuestro bot
        return "8915397259:AAGqkLWOyBF7vStNGjAfc7BIjZApECA9smQ";
    }
    public static void main(String[] args)
            throws TelegramApiException {
// Se crea un nuevo Bot API
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
// Se registra el bot
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
