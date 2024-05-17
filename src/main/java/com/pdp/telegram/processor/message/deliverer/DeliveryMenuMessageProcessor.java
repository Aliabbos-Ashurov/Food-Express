package com.pdp.telegram.processor.message.deliverer;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramDeliverer.TelegramDeliverer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramDeliverer.TelegramDelivererService;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.State;
import com.pdp.telegram.state.telegramDeliverer.ActiveOrderManagementState;
import com.pdp.telegram.state.telegramDeliverer.DeliveryMenuState;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pdp.web.enums.OrderStatus;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:18
 **/
public class DeliveryMenuMessageProcessor implements Processor<DeliveryMenuState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private final CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();
    private final TelegramDelivererService telegramDelivererService = ThreadSafeBeansContainer.telegramDelivererServiceThreadLocal.get();

    @Override
    public void process(Update update, DeliveryMenuState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        String text = message.text();
        switch (state) {
            case VIEW_ASSIGNED_ORDERS -> handleViewAssignedOrders(chatID, text);
            case VIEW_ACTIVE_ORDERS -> handleViewActiveOrders(chatID, text);
        }
    }

    private void handleViewAssignedOrders(Long chatID, String text) {
        if (checkLocalizedMessage(text, "button.back", chatID)) {
            handleBackToMainMenu(chatID);
        }
    }

    private void handleViewActiveOrders(@NonNull Long chatID, @NonNull String text) {
        if (checkLocalizedMessage(text, "button.back", chatID)) {
            handleBackToMainMenu(chatID);
        } else if (checkLocalizedMessage(text, "order.got.place", chatID)) {
            handleOrderGotPlace(chatID);
        } else if (checkLocalizedMessage(text, "order.failed", chatID)) {
            handleOrderFailed(chatID);
        }
    }

    private void handleOrderGotPlace(@NonNull Long chatID) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(ActiveOrderManagementState.CONFIRM_ORDER_PICKUP);
        telegramUserService.update(telegramUser);

        TelegramDeliverer telegramDeliverer = telegramDelivererService.getDeliverByTelegramId(telegramUser.getId());

        CustomerOrder currentOrder = getCurrentOrder(telegramDeliverer.getId());
        updateOrderStatus(currentOrder, OrderStatus.IN_TRANSIT);
        bot.execute(SendMessageFactory.sendMessageOrderProcess2(chatID, getTelegramUserLanguage(chatID)));
        notifyUserOrderInTransit(currentOrder);
    }

    private void updateOrderStatus(@NonNull CustomerOrder customerOrder, @NonNull OrderStatus status) {
        customerOrder.setOrderStatus(status);
        customerOrderService.update(customerOrder);
    }

    private void notifyUserOrderInTransit(CustomerOrder currentOrder) {
        TelegramUser tgUser = telegramUserService.getByID(currentOrder.getUserID());
        bot.execute(new SendMessage(tgUser.getChatID(), MessageSourceUtils.getLocalizedMessage("alert.user.order.in.transit", tgUser.getLanguage())));
    }

    private void handleOrderFailed(Long chatID) {
        updateTelegramUserState(chatID, ActiveOrderManagementState.DELIVERY_FAILED);
        bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("order.failed.reason", getTelegramUserLanguage(chatID))));
    }

    private CustomerOrder getCurrentOrder(UUID telegramDelivereID) {
        List<CustomerOrder> processByDeliverer = customerOrderService.getOrdersInProcessByDeliverer(telegramDelivereID);
        return processByDeliverer.stream()
                .filter(customerOrder -> customerOrder.getOrderStatus().equals(OrderStatus.YOUR_ORDER_RECEIVED))
                .findFirst()
                .orElse(null);
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
