package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.java.console.ListUtils;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.telegramUser.*;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.enums.Language;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.model.order.Order;
import com.pdp.web.service.brand.BrandService;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pdp.web.service.order.OrderService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.NonNull;

import static com.pdp.telegram.state.telegramUser.OrderPlacementState.*;

import java.util.List;
import java.util.Objects;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  19:52
 **/
public class OrderPlacementMessageProcessor implements Processor<OrderPlacementState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private final BrandService brandService = ThreadSafeBeansContainer.brandServiceThreadLocal.get();
    private final CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();
    private final OrderService orderService = ThreadSafeBeansContainer.orderServiceThreadLocal.get();

    @Override
    public void process(Update update, OrderPlacementState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        String text = message.text();
        if (Objects.requireNonNull(state) == VIEW_CART) handleViewCart(text, chatID);
    }

    private void handleViewCart(String text, Long chatID) {
        if (checkLocalizedMessage(text, "button.back", chatID)) handleBackToMain(chatID);
        else if (checkLocalizedMessage(text, "alert.make.order", chatID)) {
            handleMakeOrder(chatID);
        } else if (checkLocalizedMessage(text, "alert.clear.cart", chatID)) {
            handleClearCart(chatID);
        } else invalidSelectionSender(chatID);
    }

    private void handleMakeOrder(Long chatID) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        if (checkNotConfirmedOrder(chatID)) {
            telegramUser.setState(ConfirmOrderState.REQUEST_PAYMENT_TYPE);
            telegramUserService.update(telegramUser);
            bot.execute(SendMessageFactory.sendMessagePaymentType(chatID, getTelegramUserLanguage(chatID)));
        } else {
            bot.execute(SendMessageFactory.sendMessageCartIsEmpty(chatID,getTelegramUserLanguage(chatID)));
        }
    }

    private void handleClearCart(Long chatID) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        if (checkNotConfirmedOrder(chatID)) {
            telegramUser.setState(ConfirmationState.ACCEPT_CLEAR_CART);
            telegramUserService.update(telegramUser);
            bot.execute(SendMessageFactory.sendMessageConfirmation(chatID, getTelegramUserLanguage(chatID)));
        } else {
            bot.execute(SendMessageFactory.sendMessageCartIsEmpty(chatID,getTelegramUserLanguage(chatID)));
        }
    }

    private void handleBackToMain(Long chatID) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(UserMenuOptionState.PLACE_ORDER);
        telegramUserService.update(telegramUser);
        bot.execute(SendMessageFactory.sendMessageOrderPlacementMenu(chatID, getTelegramUserLanguage(chatID)));
    }

    private boolean checkNotConfirmedOrder(@NonNull Long chatID) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        CustomerOrder notConfirmedOrder = customerOrderService.getNotConfirmedOrder(telegramUser.getId());
        if (Objects.isNull(notConfirmedOrder)) return false;
        List<Order> orders = orderService.getOrdersByCustomerID(notConfirmedOrder.getId());
        return ListUtils.checkDataForNotNull(orders);
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
