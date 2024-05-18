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
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.enums.Language;
import com.pdp.enums.OrderStatus;
import com.pdp.enums.telegram.DeliveryStatus;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.model.description.Description;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pdp.web.service.description.DescriptionService;
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
 * Date: 14/May/2024  18:19
 **/
public class ActivateOrderMessageProcessor implements Processor<ActiveOrderManagementState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private final TelegramDelivererService telegramDelivererService = ThreadSafeBeansContainer.telegramDelivererServiceThreadLocal.get();
    private final CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();
    private final DescriptionService descriptionService = ThreadSafeBeansContainer.descriptionServiceThreadLocal.get();

    @Override
    public void process(Update update, ActiveOrderManagementState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        switch (state) {
            case CONFIRM_ORDER_PICKUP -> handleConfirmOrderDelivery(chatID, message.text());
            case CONFIRM_ORDER_DELIVERY -> {
            }
            case DELIVERY_FAILED -> handleOrderFailed(chatID, message.text(), true);
        }
    }

    private void handleConfirmOrderDelivery(Long chatID, String text) {
        if (checkLocalizedMessage(text, "order.delivered", chatID)) {
            orderDelivered(chatID);
        } else if (checkLocalizedMessage(text, "order.failed", chatID)) {
            handleOrderFailed(chatID, "", false);
        }
    }

    private void orderDelivered(Long chatID) {
        backToDeliverMenu(chatID);

        TelegramUser telegramUser = getTelegramUser(chatID);
        TelegramDeliverer telegramDeliverer = telegramDelivererService.getDeliverByTelegramId(telegramUser.getId());
        telegramDeliverer.setDeliveryStatus(DeliveryStatus.EMPTY);
        telegramDelivererService.update(telegramDeliverer);

        CustomerOrder currentOrder = getCurrentOrder(telegramDeliverer.getId(), OrderStatus.IN_TRANSIT);
        currentOrder.setOrderStatus(OrderStatus.DELIVERED);
        customerOrderService.update(currentOrder);

        bot.execute(SendMessageFactory.sendMessageDeliverMenu(chatID, getTelegramUserLanguage(chatID)));

        TelegramUser tgUser = telegramUserService.getByID(currentOrder.getUserID());
        bot.execute(new SendMessage(tgUser.getChatID(), MessageSourceUtils.getLocalizedMessage("alert.order.delivered", tgUser.getLanguage())));
    }

    private void handleOrderFailed(Long chatID, String text, boolean check) {
        backToDeliverMenu(chatID);

        TelegramUser telegramUser = getTelegramUser(chatID);
        TelegramDeliverer telegramDeliverer = telegramDelivererService.getDeliverByTelegramId(telegramUser.getId());
        telegramDeliverer.setDeliveryStatus(DeliveryStatus.EMPTY);
        telegramDelivererService.update(telegramDeliverer);
        CustomerOrder currentOrder = getCurrentOrder(telegramDeliverer.getId(), check ? OrderStatus.YOUR_ORDER_RECEIVED : OrderStatus.IN_TRANSIT);
        Description description = new Description("ORDER FAILED", text);
        descriptionService.add(description);
        currentOrder.setDescriptionID(description.getId());
        currentOrder.setOrderStatus(OrderStatus.FAILED_DELIVERY);
        customerOrderService.update(currentOrder);

        TelegramUser tgUser = telegramUserService.getByID(currentOrder.getUserID());
        String message = MessageSourceUtils.getLocalizedMessage("alert.user.order.failed", tgUser.getLanguage());
        bot.execute(new SendMessage(tgUser.getChatID(), message + ": " + text));
    }

    private void backToDeliverMenu(Long chatID) {
        updateTelegramUserState(chatID, DefaultState.BASE_DELIVERER_MENU);
        bot.execute(SendMessageFactory.sendMessageDeliverMenu(chatID, getTelegramUserLanguage(chatID)));
    }

    private CustomerOrder getCurrentOrder(UUID telegramDelivererID, OrderStatus orderStatus) {
        List<CustomerOrder> processByDeliverer = customerOrderService.getOrdersInProcessByDeliverer(telegramDelivererID);
        return processByDeliverer.stream()
                .filter(customerOrder -> customerOrder.getOrderStatus().equals(orderStatus))
                .findFirst()
                .orElse(null);
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
