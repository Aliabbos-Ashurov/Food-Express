package com.pdp.telegram;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.telegram.handler.UpdateHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;


/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  09:20
 **/
public class BotRun {
    private static final TelegramBot bot = TelegramBotConfiguration.get();

    public static void main(String[] args) {
        UpdateHandler updateHandler = new UpdateHandler();
        bot.setUpdatesListener(updates -> {
            updateHandler.handle(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        },Exception::printStackTrace);
    }
}
