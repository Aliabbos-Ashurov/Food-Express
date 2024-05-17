package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.State;
import com.pdp.telegram.state.telegramDeliverer.DeliveryMenuState;
import com.pdp.telegram.state.telegramUser.UserMenuOptionState;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import java.util.Objects;

public class DefaultMessageProcessor implements Processor<DefaultState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();

    @Override
    public void process(Update update, DefaultState state) {
        Message message = update.message();
        Long chatID = message.from().id();
        TelegramUser telegramUser = telegramUserService.findByChatID(chatID);
        String text = message.text();

        switch (state) {
            case SELECT_LANGUAGE -> processLanguageSelection(text, chatID, telegramUser);
            case BASE_USER_MENU -> processUserMenuSelection(text, chatID, telegramUser);
            case BASE_DELIVERER_MENU -> processDelivererMenuSelection(text, chatID, telegramUser);
            default -> {
            }
        }
    }

    private void processLanguageSelection(String text, Long chatID, TelegramUser telegramUser) {
        Language selectedLanguage = null;
        if (text == null) return;
        if (Objects.equals(text.substring(0, 2), "UZ")) {
            selectedLanguage = Language.UZ;
        } else if (Objects.equals(text.substring(0, 2), "EN")) {
            selectedLanguage = Language.EN;
        }
        if (selectedLanguage != null) {
            telegramUser.setLanguage(selectedLanguage);
            telegramUser.setState(DefaultState.BASE_USER_MENU);
            telegramUserService.update(telegramUser);
            bot.execute(SendMessageFactory.sendMessageWithUserMenu(chatID, getLanguage(telegramUser)));
        } else {
            bot.execute(SendMessageFactory.sendMessageSelectLanguageMenu(chatID));
        }
    }

    private void processUserMenuSelection(String text, Long chatID, TelegramUser telegramUser) {
        if (checkLocalizedMessage(text, "button.placeOrder", telegramUser)) {
            updateTelegramUserState(telegramUser, UserMenuOptionState.PLACE_ORDER);
            bot.execute(SendMessageFactory.sendMessageOrderPlacementMenu(chatID, getLanguage(telegramUser)));
        } else if (checkLocalizedMessage(text, "button.myOrders", telegramUser)) {
            updateTelegramUserState(telegramUser, UserMenuOptionState.VIEW_MY_ORDERS);
            bot.execute(SendMessageFactory.sendMessageMyOderMenu(chatID, getLanguage(telegramUser)));
        } else if (checkLocalizedMessage(text, "button.registerCourier", telegramUser)) {
            updateTelegramUserState(telegramUser, UserMenuOptionState.REGISTER_AS_COURIER);
            bot.execute(SendMessageFactory.sendMessageEnterFullname(chatID, getLanguage(telegramUser)));
        }
    }

    private void updateTelegramUserState(TelegramUser telegramUser, State state) {
        telegramUser.setState(state);
        telegramUserService.update(telegramUser);
    }

    private void processDelivererMenuSelection(String text, Long chatID, TelegramUser telegramUser) {
        if (checkLocalizedMessage(text, "button.assigned", telegramUser)) {
            telegramUser.setState(DeliveryMenuState.VIEW_ASSIGNED_ORDERS);
            telegramUserService.update(telegramUser);
//            bot.execute()
        }
    }

    private Language getLanguage(TelegramUser telegramUser) {
        return telegramUser.getLanguage();
    }

    private boolean checkLocalizedMessage(String message, String key, TelegramUser telegramUser) {
        String localizedMessage = MessageSourceUtils.getLocalizedMessage(key, getLanguage(telegramUser));
        return Objects.equals(localizedMessage, message);
    }
}
