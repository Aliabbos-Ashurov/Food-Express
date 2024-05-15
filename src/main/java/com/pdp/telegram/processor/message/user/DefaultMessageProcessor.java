package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.DefaultState;
import com.pdp.utils.factory.SendMessageFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:05
 **/
public class DefaultMessageProcessor implements Processor<DefaultState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService userService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();

    @Override
    public void process(Update update, DefaultState state) {
        Message message = update.message();
        Long chatID = message.from().id();
        TelegramUser telegramUser = userService.findByChatID(chatID);
        if (state.equals(DefaultState.SELECT_LANGUAGE)) {
            bot.execute(SendMessageFactory.sendMessageSelectLanguageMenu(chatID));
        } else if (state.equals(DefaultState.BASE_USER_MENU)) {

        } else if (state.equals(DefaultState.BASE_DELIVERER_MENU)) {

        }
    }
}
