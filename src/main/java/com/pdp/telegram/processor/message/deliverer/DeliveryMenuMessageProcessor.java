package com.pdp.telegram.processor.message.deliverer;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.State;
import com.pdp.telegram.state.telegramDeliverer.DeliveryMenuState;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.web.enums.Language;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import lombok.NonNull;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:18
 **/
public class DeliveryMenuMessageProcessor implements Processor<DeliveryMenuState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();

    @Override
    public void process(Update update, DeliveryMenuState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(DeliveryMenuState.VIEW_ASSIGNED_ORDERS)) {

        } else if (state.equals(DeliveryMenuState.VIEW_ACTIVE_ORDERS)) {

        }
    }

    private void handleBackToMainMenu(@NonNull Long chatID) {
        updateTelegramUserState(chatID, DefaultState.BASE_USER_MENU);
        Language telegramUserLanguage = getTelegramUserLanguage(chatID);
        bot.execute(SendMessageFactory.sendMessageWithUserMenu(chatID, telegramUserLanguage));
    }

    private void updateTelegramUserState(@NonNull Long chatID, @NonNull State state) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(state);
        telegramUserService.update(telegramUser);
    }

    private TelegramUser getTelegramUser(Long chatID) {
        return telegramUserService.findByChatID(chatID);
    }

    private Language getTelegramUserLanguage(Long chatID) {
        return getTelegramUser(chatID).getLanguage();
    }
}
