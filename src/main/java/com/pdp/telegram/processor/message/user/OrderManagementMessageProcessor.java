package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.telegramUser.OrderManagementState;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:09
 **/
public class OrderManagementMessageProcessor implements Processor<OrderManagementState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService userService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();

    @Override
    public void process(Update update, OrderManagementState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(OrderManagementState.CONFIRM_ORDER_ACCEPTANCE)) {

        } else if (state.equals(OrderManagementState.CLEAR_CART_CONFIRMATION)) {

        }
    }
}
