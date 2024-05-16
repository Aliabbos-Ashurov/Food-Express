package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.telegramUser.OrderPlacementState;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

import java.util.Objects;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  19:52
 **/
public class OrderPlacementMessageProcessor implements Processor<OrderPlacementState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();

    @Override
    public void process(Update update, OrderPlacementState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        TelegramUser telegramUser = telegramUserService.findByChatID(chatID);
        String text = message.text();
        if (state.equals(OrderPlacementState.DEFAULT_ORDER_PLACEMENT)) {
            if (checkLocalizedMessage(text, "button.select.brand", telegramUser)) {
                telegramUser.setState(OrderPlacementState.SELECT_BRAND);
                telegramUserService.update(telegramUser);
                bot.execute(SendMessageFactory.sendMessageWithBrandsMenu(chatID, getLanguage(telegramUser)));
            }else if (checkLocalizedMessage(text,"button.view.cart", telegramUser)) {
                telegramUser.setState(OrderPlacementState.VIEW_CART);
                telegramUserService.update(telegramUser);

            }else if (checkLocalizedMessage(text,"button.back", telegramUser)) {
//                telegramUser.setState();
            }
        } else if (state.equals(OrderPlacementState.SELECT_BRAND)) {

        } else if (state.equals(OrderPlacementState.VIEW_CART)) {

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
