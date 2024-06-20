package com.pdp.config;

import com.pengrad.telegrambot.TelegramBot;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Configuration class for initializing and providing a {@link TelegramBot} instance.
 * <p>
 * This class uses a {@link ThreadLocal} to ensure that each thread gets its own instance of {@link TelegramBot},
 * initialized with a token read from a resource bundle.
 * Author: Doniyor Nishonov
 * Date: 14/May/2024 16:18
 * </p>
 */
public class TelegramBotConfiguration {
    private static final ResourceBundle settings = ResourceBundle.getBundle("settings/settings", Locale.ENGLISH);
    private static final ThreadLocal<TelegramBot> telegramBotThreadLocal = ThreadLocal.withInitial(() -> new TelegramBot(settings.getString("bot.token")));

    public static TelegramBot get() {
        return telegramBotThreadLocal.get();
    }
}
