package com.pdp.telegram.handler;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  16:00
 **/
public class UpdateHandler {
    public void handle(List<Update> updates) {
        CompletableFuture.runAsync(() -> {
            for (Update update : updates) {
                if (Objects.nonNull(update.message())) {
                    ThreadSafeBeansContainer.messageHandlerThreadLocal.get().handle(update);
                } else if (Objects.nonNull(update.callbackQuery())) {
                    ThreadSafeBeansContainer.callbackHandlerThreadLocal.get().handle(update);
                }
            }
        }, ThreadSafeBeansContainer.executor);
    }
}
