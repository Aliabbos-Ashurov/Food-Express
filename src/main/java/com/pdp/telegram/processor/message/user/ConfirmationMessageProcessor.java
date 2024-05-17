package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.State;
import com.pdp.telegram.state.telegramUser.ConfirmationState;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.model.order.Order;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pdp.web.service.order.OrderService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.NonNull;

import java.util.Objects;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:17
 **/
public class ConfirmationMessageProcessor implements Processor<ConfirmationState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private final OrderService orderService = ThreadSafeBeansContainer.orderServiceThreadLocal.get();
    private final CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();

    @Override
    public void process(Update update, ConfirmationState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        String text = message.text();
        switch (state) {
            case ACCEPT_ORDER_IN_CART -> handleAcceptOrderInCart(text, chatID);
            case ACCEPT_CLEAR_CART -> handleAcceptClearCart(text, chatID);
        }
    }

    private void handleAcceptOrderInCart(String text, @NonNull Long chatID) {

    }

    private void handleAcceptClearCart(String text, @NonNull Long chatID) {
        System.out.println(text);
        if (checkLocalizedMessage(text, "button.yes", chatID)) {
            TelegramUser telegramUser = getTelegramUser(chatID);
            CustomerOrder notConfirmedOrder = customerOrderService.getNotConfirmedOrder(telegramUser.getId());
            orderService.clearByCustomer(notConfirmedOrder.getId());
            bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("alert.cart.cleaned", getTelegramUserLanguage(chatID))));
            updateTelegramUserState(chatID, DefaultState.BASE_USER_MENU);
            bot.execute(SendMessageFactory.sendMessageWithUserMenu(chatID, getTelegramUserLanguage(chatID)));
        } else if (checkLocalizedMessage(text, "button.no", chatID)) {
            updateTelegramUserState(chatID, DefaultState.BASE_USER_MENU);
            bot.execute(SendMessageFactory.sendMessageWithUserMenu(chatID, getTelegramUserLanguage(chatID)));
        } else invalidSelectionSender(chatID);
    }

    private TelegramUser updateTelegramUserState(@NonNull Long chatID, @NonNull State state) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(state);
        telegramUserService.update(telegramUser);
        return telegramUser;
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

    private void invalidSelectionSender(Long chatID) {
        bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("error.invalidSelection", getTelegramUserLanguage(chatID))));
    }
}
