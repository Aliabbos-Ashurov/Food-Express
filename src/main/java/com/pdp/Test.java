package com.pdp;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.utils.factory.SendPhotoFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since: 30/April/2024  21:09
 **/
public class Test {
    private static final TelegramBot telegramBot = TelegramBotConfiguration.get();

    public static void main(String[] args) {
        telegramBot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                Message message = update.message();
                if (message != null) {
                    User from = message.from();
                    Long id = from.id();
                    telegramBot.execute(SendPhotoFactory.sendPhotoCategoryWithFoodsButton(id, UUID.fromString("f60fc7f6-930b-4d00-ad64-2526e1459bce"), "LANCHBOKS"));
                } else {
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
