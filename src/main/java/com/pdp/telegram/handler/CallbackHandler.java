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
import com.pengrad.telegrambot.model.message.MaybeInaccessibleMessage;


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
        MaybeInaccessibleMessage maybeInaccessibleMessage = callbackQuery.maybeInaccessibleMessage();
        Long chatID = maybeInaccessibleMessage.chat().id();
        TelegramUser curUser = telegramUserService.findByChatID(chatID);
        State state = curUser.getState();
        switch (state) {
            case UserViewState userViewState ->
                    ThreadSafeBeansContainer.userViewCallbackProcessor.get().process(update, userViewState);
            case ConfirmationState confirmationState ->
                    ThreadSafeBeansContainer.confirmationCallbackProcessor.get().process(update, confirmationState);
            case DeliveryMenuState deliveryMenuState ->
                    ThreadSafeBeansContainer.deliveryMenuCallbackProcessor.get().process(update, deliveryMenuState);
            case ActiveOrderManagementState activateOrder ->
                    ThreadSafeBeansContainer.activateOrderCallbackProcessor.get().process(update, activateOrder);
            case null, default -> {
            }
        }
    }
}
