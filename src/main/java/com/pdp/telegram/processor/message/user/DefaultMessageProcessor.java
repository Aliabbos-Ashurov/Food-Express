package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramDeliverer.TelegramDeliverer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramDeliverer.TelegramDelivererService;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.State;
import com.pdp.telegram.state.telegramDeliverer.DeliveryMenuState;
import com.pdp.telegram.state.telegramUser.UserMenuOptionState;
import com.pdp.utils.factory.ReplyKeyboardMarkupFactory;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class DefaultMessageProcessor implements Processor<DefaultState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private final TelegramDelivererService telegramDelivererService = ThreadSafeBeansContainer.telegramDelivererServiceThreadLocal.get();

    @Override
    public void process(Update update, DefaultState state) {
        Message message = update.message();
        Long chatID = message.from().id();
        String text = message.text();

        switch (state) {
            case SELECT_LANGUAGE -> processLanguageSelection(text, chatID);
            case BASE_USER_MENU -> processUserMenuSelection(text, chatID);
            case BASE_DELIVERER_MENU -> processDelivererMenuSelection(text, chatID);
            default -> {
            }
        }
    }

    private void processLanguageSelection(String text, Long chatID) {
        TelegramUser telegramUser = getTelegramUser(chatID);
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
            bot.execute(SendMessageFactory.sendMessageWithUserMenu(chatID, getTelegramUserLanguage(chatID)));
        } else {
            bot.execute(SendMessageFactory.sendMessageSelectLanguageMenu(chatID));
        }
    }

    private void processUserMenuSelection(String text, Long chatID) {
        if (checkLocalizedMessage(text, "button.placeOrder", chatID)) {
            updateTelegramUserState(chatID, UserMenuOptionState.PLACE_ORDER);
            bot.execute(SendMessageFactory.sendMessageOrderPlacementMenu(chatID, getTelegramUserLanguage(chatID)));
        } else if (checkLocalizedMessage(text, "button.myOrders", chatID)) {
            updateTelegramUserState(chatID, UserMenuOptionState.VIEW_MY_ORDERS);
            bot.execute(SendMessageFactory.sendMessageMyOderMenu(chatID, getTelegramUserLanguage(chatID)));
        } else if (checkLocalizedMessage(text, "button.registerCourier", chatID)) {
            updateTelegramUserState(chatID, UserMenuOptionState.REGISTER_AS_COURIER);
            bot.execute(SendMessageFactory.sendMessageEnterFullname(chatID, getTelegramUserLanguage(chatID)));
        }
    }

    private void updateTelegramUserState(Long chatID, State state) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(state);
        telegramUserService.update(telegramUser);
    }

    private void processDelivererMenuSelection(String text, Long chatID) {
        if (checkLocalizedMessage(text, "button.assigned", chatID)) {
            updateTelegramUserState(chatID, DeliveryMenuState.VIEW_ASSIGNED_ORDERS);
            Language language = getTelegramUserLanguage(chatID);
            List<SendMessage> sendMessages = SendMessageFactory.sendMessagesOrdersToDeliverer(chatID, language);
            processMessages(sendMessages, () -> SendMessageFactory.sendMessageNoOrderForDeliverer(chatID, language), chatID, language);
        } else if (checkLocalizedMessage(text, "button.active.order", chatID)) {
            updateTelegramUserState(chatID, DeliveryMenuState.VIEW_ACTIVE_ORDERS);
            Language language = getTelegramUserLanguage(chatID);
            List<SendMessage> sendMessages = SendMessageFactory.sendMessagesOrdersInProcessForDeliverer(chatID, getTelegramDeliverer(chatID).getId(), language);
            processMessagesActive(sendMessages, () -> SendMessageFactory.sendMessageNoOrderActiveForDeliverer(chatID, language), chatID, language);
        }
    }

    private void processMessagesActive(List<SendMessage> sendMessages, Supplier<SendMessage> supplier, Long chatID, Language language) {
        Optional.ofNullable(sendMessages)
                .filter(messages -> !messages.isEmpty())
                .ifPresentOrElse(
                        messages -> {
                            messages.forEach(bot::execute);
                            bot.execute(SendMessageFactory.sendMessageOrderProcess1(chatID, language));
                        },
                        () -> bot.execute(supplier.get())
                );
    }

    private void processMessages(List<SendMessage> sendMessages, Supplier<SendMessage> supplier, Long chatID, Language language) {
        Optional.ofNullable(sendMessages)
                .filter(messages -> !messages.isEmpty())
                .ifPresentOrElse(
                        messages -> {
                            messages.forEach(bot::execute);
                            bot.execute(SendMessageFactory.createMessage(chatID, "------", ReplyKeyboardMarkupFactory.backButton(language)));
                        },
                        () -> bot.execute(supplier.get())
                );
    }

    private TelegramDeliverer getTelegramDeliverer(Long chatID) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        return telegramDelivererService.getDeliverByTelegramId(telegramUser.getId());
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
