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
import com.pdp.utils.factory.SendMessageFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

import java.util.Objects;


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
        TelegramUser telegramUser = telegramUserService.findByChatID(chatId);
        if (Objects.isNull(telegramUser)) telegramUser = registerTelegramUser(user, chatId);
        State state = telegramUser.getState();
        if (state == null) {
            startRegister(telegramUser);
        } else if (state instanceof DefaultState defaultState) {
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
        } else if (state instanceof OrderManagementState orderManagementState) {
            ThreadSafeBeansContainer.orderManagementMessageProcessor.get().process(update, orderManagementState);
        } else if (state instanceof DeliveryMenuState deliveryMenuState) {
            ThreadSafeBeansContainer.deliveryMenuMessageProcessor.get().process(update, deliveryMenuState);
        } else if (state instanceof ActiveOrderManagementState activateOrder) {
            ThreadSafeBeansContainer.activateOrderMessageProcessor.get().process(update, activateOrder);
        }
    }

    private void startRegister(TelegramUser telegramUser) {
        telegramUser.setState(DefaultState.SELECT_LANGUAGE);
        telegramUserService.update(telegramUser);
        bot.execute(SendMessageFactory.sendMessageSelectLanguageMenu(telegramUser.getChatID()));
    }

    private TelegramUser registerTelegramUser(User user, Long chatId) {
        TelegramUser telegramUser = TelegramUser.builder()
                .firstName(user.firstName())
                .username(user.username())
                .chatID(chatId)
                .build();
        telegramUserService.add(telegramUser);
        return telegramUser;
    }
}
