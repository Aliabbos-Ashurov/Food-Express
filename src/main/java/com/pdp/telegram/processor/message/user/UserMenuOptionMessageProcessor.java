package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.java.console.ListUtils;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.telegramUser.MyOrderState;
import com.pdp.telegram.state.telegramUser.UserMenuOptionState;
import com.pdp.utils.factory.ReplyKeyboardMarkupFactory;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:07
 **/
public class UserMenuOptionMessageProcessor implements Processor<UserMenuOptionState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();

    @Override
    public void process(Update update, UserMenuOptionState state) {
        Message message = update.message();
        String text = message.text();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(UserMenuOptionState.PLACE_ORDER)) {

        } else if (state.equals(UserMenuOptionState.VIEW_MY_ORDERS)) {
            if (checkLocalizedMessage("button.user.order.active", text, chatID))
                handleViewActiveOrders(chatID);
            else if (checkLocalizedMessage("button.user.order.archive", text, chatID))
                handleViewArchivedOrders(chatID);
        } else if (state.equals(UserMenuOptionState.REGISTER_AS_COURIER)) {

        }
    }

    private void handleViewActiveOrders(Long chatID) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(MyOrderState.VIEW_ACTIVE_ORDERS);
        telegramUserService.update(telegramUser);
        Language telegramUserLanguage = getTelegramUserLanguage(chatID);
        List<SendMessage> sendMessages = SendMessageFactory.sendMessagesOrdersInProcessForUser(chatID, telegramUser.getId(), telegramUserLanguage);
        processMessages(sendMessages, () -> SendMessageFactory.sendMessageCartIsEmpty(chatID, telegramUserLanguage), chatID, telegramUserLanguage);
    }

    private void handleViewArchivedOrders(Long chatID) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(MyOrderState.VIEW_ARCHIVED_ORDERS);
        telegramUserService.update(telegramUser);
        Language telegramUserLanguage = getTelegramUserLanguage(chatID);
        List<SendMessage> sendMessages = SendMessageFactory.sendMessagesUserArchive(chatID, telegramUser.getId(), telegramUserLanguage);
        processMessages(sendMessages, () -> SendMessageFactory.sendMessageNotArchiveOrders(chatID, telegramUserLanguage), chatID, telegramUserLanguage);
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
