package com.pdp.telegram.processor;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.State;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  17:32
 **/
public interface Processor<S extends State> {
    TelegramBot bot = TelegramBotConfiguration.get();
    TelegramUserService userService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();

    void process(Update update, S state);
}
