package com.pdp.telegram.processor.message.deliverer;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.State;
import com.pdp.telegram.state.telegramDeliverer.DeliveryMenuState;
import com.pdp.telegram.state.telegramUser.UserMenuOptionState;
import com.pdp.telegram.state.telegramUser.UserViewState;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pdp.web.model.order.Order;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import lombok.NonNull;

import java.util.Objects;

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
        String text = message.text();
        if (state.equals(DeliveryMenuState.VIEW_ASSIGNED_ORDERS)) {
            if (checkLocalizedMessage(text, "button.back", chatID)) {
                handleBackToMainMenu(chatID);
                return;
            }
        } else if (state.equals(DeliveryMenuState.VIEW_ACTIVE_ORDERS)) {
            if (checkLocalizedMessage(text, "button.back", chatID)) {
                handleBackToMainMenu(chatID);
                return;
            }
            if (checkLocalizedMessage(text, "order.got.place", chatID)) {


            } else if (checkLocalizedMessage(text, "order.failed", chatID)) {

            }
        }
    }

    private void handleBackToMainMenu(@NonNull Long chatID) {
        updateTelegramUserState(chatID, DefaultState.BASE_DELIVERER_MENU);
        Language telegramUserLanguage = getTelegramUserLanguage(chatID);
        bot.execute(SendMessageFactory.sendMessageDeliverMenu(chatID, telegramUserLanguage));
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

    private boolean checkLocalizedMessage(String message, String key, Long chatID) {
        String localizedMessage = MessageSourceUtils.getLocalizedMessage(key, getTelegramUserLanguage(chatID));
        return Objects.equals(localizedMessage, message);
    }
}
