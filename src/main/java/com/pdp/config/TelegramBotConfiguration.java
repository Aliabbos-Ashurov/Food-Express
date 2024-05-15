package com.pdp.config;

import com.pengrad.telegrambot.TelegramBot;

import java.util.ResourceBundle;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  16:18
 **/
public class TelegramBotConfiguration {
    private static final ResourceBundle settings = ResourceBundle.getBundle("settings");
    private static final ThreadLocal<TelegramBot> telegramBotThreadLocal = ThreadLocal.withInitial(() -> new TelegramBot(settings.getString("bot.token")));

    public static TelegramBot get() {
        return telegramBotThreadLocal.get();
    }
}
