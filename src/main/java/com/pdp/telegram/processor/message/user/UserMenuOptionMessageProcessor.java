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
import com.pdp.telegram.state.telegramUser.*;
import com.pdp.utils.factory.ReplyKeyboardMarkupFactory;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:07
 **/
public class UserMenuOptionMessageProcessor implements Processor<UserMenuOptionState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private final TelegramDelivererService telegramDelivererService = ThreadSafeBeansContainer.telegramDelivererServiceThreadLocal.get();

    @Override
    public void process(Update update, UserMenuOptionState state) {
        Message message = update.message();
        String text = message.text();
        User user = message.from();
        Long chatID = user.id();
        switch (state) {
            case PLACE_ORDER -> processPlaceOrder(text, chatID);
            case VIEW_MY_ORDERS -> processViewMyOrders(text, chatID);
            case REGISTER_AS_COURIER -> processCourierRegistration(text, chatID);
        }
    }

    private void processCourierRegistration(String text, Long chatID) {
        if (fullNameMatcher(text)) {
            buildDeliverer(chatID, text);
            updateTelegramUserState(chatID, CourierRegistrationState.ENTER_PHONE_NUMBER);
            bot.execute(SendMessageFactory.sendMessageContact(chatID, getTelegramUserLanguage(chatID)));
        } else fullNameNotMatchedSender(chatID);
    }

    private void processViewMyOrders(String text, Long chatID) {
        if (checkLocalizedMessage("button.user.order.active", text, chatID))
            handleViewActiveOrders(chatID);
        else if (checkLocalizedMessage("button.user.order.archive", text, chatID))
            handleViewArchivedOrders(chatID);
        else if (checkLocalizedMessage("button.back", text, chatID))
            handleBackToMainMenu(chatID);
        else invalidSelectionSender(chatID);
    }

    private void processPlaceOrder(String text, Long chatID) {
        if (checkLocalizedMessage("button.cart", text, chatID)) {
            updateTelegramUserState(chatID, OrderPlacementState.VIEW_CART);
            bot.execute(SendMessageFactory.sendMessageOrderManagementMenu(chatID, getTelegramUserLanguage(chatID)));
            bot.execute(SendMessageFactory.sendMessageUserOrderNotConfirmed(chatID, getTelegramUser(chatID).getId(), getTelegramUserLanguage(chatID)));
        } else if (checkLocalizedMessage("button.select.brand", text, chatID)) {
            updateTelegramUserState(chatID, UserViewState.VIEW_BRANDS);
            bot.execute(SendMessageFactory.sendMessageWithBrandsMenu(chatID, getTelegramUserLanguage(chatID)));
        } else if (checkLocalizedMessage("button.back", text, chatID)) {
            updateTelegramUserState(chatID, DefaultState.BASE_USER_MENU);
            bot.execute(SendMessageFactory.sendMessageWithUserMenu(chatID, getTelegramUserLanguage(chatID)));
        } else invalidSelectionSender(chatID);
    }

    private static boolean fullNameMatcher(String message) {
        Pattern pattern = Pattern.compile("^[A-Z][a-z]+ [A-Z][a-z]+$");
        return pattern.matcher(message).matches();
    }

    private void handleViewActiveOrders(@NonNull Long chatID) {
        TelegramUser telegramUser = updateTelegramUserState(chatID, MyOrderState.VIEW_ACTIVE_ORDERS);
        Language telegramUserLanguage = getTelegramUserLanguage(chatID);
        List<SendMessage> sendMessages = SendMessageFactory.sendMessagesOrdersInProcessForUser(chatID, telegramUser.getId(), telegramUserLanguage);
        processMessages(sendMessages, () -> SendMessageFactory.sendMessageCartIsEmpty(chatID, telegramUserLanguage), chatID, telegramUserLanguage);
    }

    private void handleViewArchivedOrders(@NonNull Long chatID) {
        TelegramUser telegramUser = updateTelegramUserState(chatID, MyOrderState.VIEW_ARCHIVED_ORDERS);
        Language telegramUserLanguage = getTelegramUserLanguage(chatID);
        List<SendMessage> sendMessages = SendMessageFactory.sendMessagesUserArchive(chatID, telegramUser.getId(), telegramUserLanguage);
        processMessages(sendMessages, () -> SendMessageFactory.sendMessageNotArchiveOrders(chatID, telegramUserLanguage), chatID, telegramUserLanguage);
    }

    private void handleBackToMainMenu(@NonNull Long chatID) {
        updateTelegramUserState(chatID, DefaultState.BASE_USER_MENU);
        Language telegramUserLanguage = getTelegramUserLanguage(chatID);
        bot.execute(SendMessageFactory.sendMessageWithUserMenu(chatID, telegramUserLanguage));
    }

    private TelegramUser updateTelegramUserState(@NonNull Long chatID, @NonNull State state) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(state);
        telegramUserService.update(telegramUser);
        return telegramUser;
    }

    private void buildDeliverer(@NonNull Long chatID, String fullName) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(CourierRegistrationState.ENTER_PHONE_NUMBER);
        TelegramDeliverer telegramDeliverer = TelegramDeliverer.builder()
                .telegramUserID(telegramUser.getId())
                .fullname(fullName)
                .build();
        telegramDelivererService.add(telegramDeliverer);
        telegramUserService.update(telegramUser);
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

    private void invalidSelectionSender(Long chatID) {
        bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("error.invalidSelection", getTelegramUserLanguage(chatID))));
    }

    private void fullNameNotMatchedSender(Long chatID) {
        bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("alert.not.match", getTelegramUserLanguage(chatID))));
    }

    private boolean checkLocalizedMessage(String key, String message, Long chatID) {
        String localizedMessage = MessageSourceUtils.getLocalizedMessage(key, getTelegramUserLanguage(chatID));
        return localizedMessage.equals(message);
    }

    private TelegramUser getTelegramUser(Long chatID) {
        return telegramUserService.findByChatID(chatID);
    }

    private Language getTelegramUserLanguage(Long chatID) {
        return getTelegramUser(chatID).getLanguage();
    }
}
