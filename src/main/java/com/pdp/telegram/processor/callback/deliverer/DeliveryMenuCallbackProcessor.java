package com.pdp.telegram.processor.callback.deliverer;

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
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.web.enums.Language;
import com.pdp.web.enums.OrderStatus;
import com.pdp.web.enums.telegram.DeliveryStatus;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.message.MaybeInaccessibleMessage;
import lombok.NonNull;

import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:18
 **/
public class DeliveryMenuCallbackProcessor implements Processor<DeliveryMenuState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private final CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();
    private final TelegramDelivererService telegramDelivererService = ThreadSafeBeansContainer.telegramDelivererServiceThreadLocal.get();

    @Override
    public void process(Update update, DeliveryMenuState state) {
        CallbackQuery callbackQuery = update.callbackQuery();
        MaybeInaccessibleMessage maybeInaccessibleMessage = callbackQuery.maybeInaccessibleMessage();
        String data = callbackQuery.data();
        Long chatID = maybeInaccessibleMessage.chat().id();
        switch (state) {
            case VIEW_ASSIGNED_ORDERS -> handleViewAssignedOrders(chatID, data);
            case VIEW_ACTIVE_ORDERS -> {
            }
        }
    }

    private void handleViewAssignedOrders(Long chatID, String data) {
        TelegramDeliverer telegramDeliverer = getTelegramDeliverer(chatID);
        if (telegramDeliverer.getDeliveryStatus().equals(DeliveryStatus.ACCEPTED)) {
            bot.execute(SendMessageFactory.sendMessageOrderCloser(chatID, getTelegramUserLanguage(chatID)));
            return;
        }
        UUID orderId = UUID.fromString(data);
        CustomerOrder customerOrder = customerOrderService.getByID(orderId);
        customerOrder.setDeliverID(telegramDeliverer.getId());
        customerOrder.setOrderStatus(OrderStatus.YOUR_ORDER_RECEIVED);
        customerOrderService.update(customerOrder);
        updateTelegramUserState(chatID, DefaultState.BASE_DELIVERER_MENU);
        telegramDeliverer.setDeliveryStatus(DeliveryStatus.ACCEPTED);
        telegramDelivererService.update(telegramDeliverer);
        bot.execute(SendMessageFactory.sendMessageOrderReceived(chatID, getTelegramUserLanguage(chatID)));
    }

    private void updateTelegramUserState(@NonNull Long chatID, @NonNull State state) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(state);
        telegramUserService.update(telegramUser);
    }

    private TelegramDeliverer getTelegramDeliverer(Long chatID) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        return telegramDelivererService.getDeliverByTelegramId(telegramUser.getId());
    }

    private TelegramUser getTelegramUser(@NonNull Long chatID) {
        return telegramUserService.findByChatID(chatID);
    }

    private Language getTelegramUserLanguage(Long chatID) {
        return getTelegramUser(chatID).getLanguage();
    }
}
