package com.pdp.telegram.processor.message.deliverer;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.telegramDeliverer.ActiveOrderManagementState;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:19
 **/
public class ActivateOrderMessageProcessor implements Processor<ActiveOrderManagementState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService userService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();

    @Override
    public void process(Update update, ActiveOrderManagementState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(ActiveOrderManagementState.CONFIRM_ORDER_PICKUP)) {

        } else if (state.equals(ActiveOrderManagementState.CONFIRM_ORDER_DELIVERY)) {

        } else if (state.equals(ActiveOrderManagementState.DELIVERY_FAILED)) {

        }
    }
}
