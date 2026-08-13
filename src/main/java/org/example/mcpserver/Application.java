package org.example.mcpserver;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Application {
    public static void main(String[] args) throws Exception {

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        botsApi.registerBot(new TelegramBot());

        System.out.println("Bot iniciado correctamente");
    }
}
