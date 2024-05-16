package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.telegramUser.MyOrderState;
import com.pdp.telegram.state.telegramUser.UserMenuOptionState;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import java.awt.desktop.AboutEvent;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:09
 **/
public class MyOrderMessageProcessor implements Processor<MyOrderState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();

    @Override
    public void process(Update update, MyOrderState state) {
        Message message = update.message();
        User user = message.from();
        String text = message.text();
        Long chatID = user.id();
        if (state.equals(MyOrderState.VIEW_ACTIVE_ORDERS) || state.equals(MyOrderState.VIEW_ARCHIVED_ORDERS)) {
            System.out.println(text);
            if (checkLocalizedMessage("button.back", text, chatID)) back(chatID);
            else invalidSelectionSender(chatID);
        }
    }

    private void invalidSelectionSender(Long chatID) {
        bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("error.invalidSelection", getTelegramUserLanguage(chatID))));
    }

    private void back(Long chatID) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(UserMenuOptionState.VIEW_MY_ORDERS);
        Language telegramUserLanguage = getTelegramUserLanguage(chatID);
        telegramUserService.update(telegramUser);
        bot.execute(SendMessageFactory.sendMessageMyOderMenu(chatID, telegramUserLanguage));
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
