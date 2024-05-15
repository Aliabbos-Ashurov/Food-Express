package com.pdp.telegram;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.telegram.handler.UpdateHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;

import java.util.ResourceBundle;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  09:20
 **/
public class BotRun {
    private static final TelegramBot bot = TelegramBotConfiguration.get();
    private static final UpdateHandler updateHandler = new UpdateHandler();

    public static void main(String[] args) {
        bot.setUpdatesListener(updates -> {
            updateHandler.handle(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
