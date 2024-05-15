package com.pdp.telegram.handler;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.State;
import com.pdp.telegram.state.telegramDeliverer.ActiveOrderManagementState;
import com.pdp.telegram.state.telegramDeliverer.DeliveryMenuState;
import com.pdp.telegram.state.telegramUser.*;
import com.pdp.web.service.user.UserService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;


/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  16:02
 **/
public class MessageHandler implements Handler {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();

    @Override
    public void handle(Update update) {
        Message message = update.message();
        User user = message.from();
        Long chatId = user.id();
        registerTelegramUser(user, chatId);
        TelegramUser telegramUser = telegramUserService.findByChatID(chatId);
        State state = telegramUser.getState();
        if (state instanceof DefaultState defaultState) {
            ThreadSafeBeansContainer.defaultMessageProcessor.get().process(update, defaultState);
        } else if (state instanceof UserMenuOptionState userMenuOptionState) {
            ThreadSafeBeansContainer.userMenuOptionMessageProcessor.get().process(update, userMenuOptionState);
        } else if (state instanceof OrderPlacementState orderPlacementState) {
            ThreadSafeBeansContainer.orderPlacementMessageProcessor.get().process(update, orderPlacementState);
        } else if (state instanceof UserViewState userViewState) {
            ThreadSafeBeansContainer.userViewMessageProcessor.get().process(update, userViewState);
        } else if (state instanceof ConfirmOrderState confirmOrderState) {
            ThreadSafeBeansContainer.confirmOrderMessageProcessor.get().process(update, confirmOrderState);
        } else if (state instanceof MyOrderState myOrderState) {
            ThreadSafeBeansContainer.myOrderMessageProcessor.get().process(update, myOrderState);
        } else if (state instanceof CourierRegistrationState courierRegistrationState) {
            ThreadSafeBeansContainer.courierRegistrationMessageProcessor.get().process(update, courierRegistrationState);
        } else if (state instanceof ConfirmationState confirmationState) {
            ThreadSafeBeansContainer.confirmationMessageProcessor.get().process(update, confirmationState);
        } else if (state instanceof DeliveryMenuState deliveryMenuState) {
            ThreadSafeBeansContainer.deliveryMenuMessageProcessor.get().process(update, deliveryMenuState);
        } else if (state instanceof ActiveOrderManagementState activateOrder) {
            ThreadSafeBeansContainer.activateOrderMessageProcessor.get().process(update, activateOrder);
        }
    }

    private void registerTelegramUser(User user, Long chatId) {
        TelegramUser telegramUser = TelegramUser.builder()
                .firstName(user.firstName())
                .username(user.username())
                .state(DefaultState.SELECT_LANGUAGE)
                .chatID(chatId)
                .build();
        telegramUserService.add(telegramUser);
    }
}
