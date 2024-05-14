package com.pdp.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendPhoto;

import java.util.ResourceBundle;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  09:20
 **/
public class BotRun {
    static final ResourceBundle settings = ResourceBundle.getBundle("settings/settings");
    private static final TelegramBot bot = new TelegramBot(settings.getString("bot.token"));

    public static void main(String[] args) {
        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                if (update.message().photo() != null) {
                    for (PhotoSize photoSize : update.message().photo()) {
                        System.out.println(photoSize.fileId());
                    }
                    System.out.println("-----------------------------------------");
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
