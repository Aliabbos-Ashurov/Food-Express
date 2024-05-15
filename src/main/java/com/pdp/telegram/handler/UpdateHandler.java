package com.pdp.telegram.handler;

import com.pengrad.telegrambot.model.Update;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static com.pdp.config.ThreadSafeBeansContainer.*;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  16:00
 **/
public class UpdateHandler {
    public void handle(List<Update> updates) {
        CompletableFuture.runAsync(() -> {
            for (Update update : updates) {
                executor.submit(() -> {
                    if (Objects.nonNull(update.message())) {
                        messageHandlerThreadLocal.get().handle(update);
                    } else if (Objects.nonNull(update.callbackQuery())) {
                        callbackHandlerThreadLocal.get().handle(update);
                    }
                });
            }
        });
    }
}
