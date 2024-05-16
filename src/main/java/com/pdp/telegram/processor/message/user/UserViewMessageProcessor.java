package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.telegramUser.UserViewState;
import com.pdp.web.service.brand.BrandService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:06
 **/
public class UserViewMessageProcessor implements Processor<UserViewState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService userService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private final BrandService brandService = ThreadSafeBeansContainer.brandServiceThreadLocal.get();
    @Override
    public void process(Update update, UserViewState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        switch (state) {
            case VIEW_BRANDS -> {

            }
            case VIEW_CATEGORIES -> {
            }
            case VIEW_FOODS -> {
            }
            case COUNT -> {
            }
        }
    }
}
