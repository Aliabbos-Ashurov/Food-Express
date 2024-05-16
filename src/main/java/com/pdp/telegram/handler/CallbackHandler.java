package com.pdp.telegram.handler;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.State;
import com.pdp.telegram.state.telegramDeliverer.ActiveOrderManagementState;
import com.pdp.telegram.state.telegramDeliverer.DeliveryMenuState;
import com.pdp.telegram.state.telegramUser.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;


/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  16:02
 **/
public class CallbackHandler implements Handler {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();

    @Override
    public void handle(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Long chatID = callbackQuery.message().chat().id();
        TelegramUser curUser = telegramUserService.findByChatID(chatID);
        State state = curUser.getState();
        if (state instanceof UserMenuOptionState userMenuOptionState) {
            ThreadSafeBeansContainer.userMenuOptionCallbackProcessor.get().process(update, userMenuOptionState);
        } else if (state instanceof OrderPlacementState orderPlacementState) {
            ThreadSafeBeansContainer.orderPlacementCallbackProcessor.get().process(update, orderPlacementState);
        } else if (state instanceof UserViewState userViewState) {
            ThreadSafeBeansContainer.userViewCallbackProcessor.get().process(update, userViewState);
        } else if (state instanceof ConfirmOrderState confirmOrderState) {
            ThreadSafeBeansContainer.confirmOrderCallbackProcessor.get().process(update, confirmOrderState);
        } else if (state instanceof MyOrderState myOrderState) {
            ThreadSafeBeansContainer.myOrderCallbackProcessor.get().process(update, myOrderState);
        } else if (state instanceof CourierRegistrationState courierRegistrationState) {
            ThreadSafeBeansContainer.courierRegistrationCallbackProcessor.get().process(update, courierRegistrationState);
        } else if (state instanceof ConfirmationState confirmationState) {
            ThreadSafeBeansContainer.confirmationCallbackProcessor.get().process(update, confirmationState);
        } else if (state instanceof OrderManagementState orderManagementState) {
            ThreadSafeBeansContainer.orderManagementCallbackProcessor.get().process(update, orderManagementState);
        } else if (state instanceof DeliveryMenuState deliveryMenuState) {
            ThreadSafeBeansContainer.deliveryMenuCallbackProcessor.get().process(update, deliveryMenuState);
        } else if (state instanceof ActiveOrderManagementState activateOrder) {
            ThreadSafeBeansContainer.activateOrderCallbackProcessor.get().process(update, activateOrder);
        }
    }
}
