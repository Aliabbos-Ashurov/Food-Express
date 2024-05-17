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
import com.pdp.web.enums.Language;
import com.pdp.web.enums.OrderStatus;
import com.pdp.web.enums.telegram.DeliveryStatus;
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
        if (state.equals(ActiveOrderManagementState.CONFIRM_ORDER_PICKUP)) {

        } else if (state.equals(ActiveOrderManagementState.CONFIRM_ORDER_DELIVERY)) {
            handleConfirmOrderDelivery(chatID, message.text());
        } else if (state.equals(ActiveOrderManagementState.DELIVERY_FAILED)) {
            handleOrderFailed(chatID, message.text());
        }
    }

    private void handleConfirmOrderDelivery(Long chatID, String text) {
        if (checkLocalizedMessage(text, "order.delivered", chatID)) {
            orderDelivered(chatID);
        } else if (checkLocalizedMessage(text, "order.failed", chatID)) {
        }
    }

    private void orderDelivered(Long chatID) {
        backToDeliverMenu(chatID);

        TelegramUser telegramUser = getTelegramUser(chatID);
        TelegramDeliverer telegramDeliverer = telegramDelivererService.getDeliverByTelegramId(telegramUser.getId());
        telegramDeliverer.setDeliveryStatus(DeliveryStatus.EMPTY);
        telegramDelivererService.update(telegramDeliverer);

        CustomerOrder currentOrder = getCurrentOrder(telegramDeliverer.getId());
        currentOrder.setOrderStatus(OrderStatus.DELIVERED);
        customerOrderService.update(currentOrder);

        TelegramUser tgUser = telegramUserService.getByID(currentOrder.getUserID());
        bot.execute(new SendMessage(tgUser.getChatID(), MessageSourceUtils.getLocalizedMessage("alert.order.delivered", tgUser.getLanguage())));
    }

    private void handleOrderFailed(Long chatID, String text) {
        backToDeliverMenu(chatID);

        TelegramUser telegramUser = getTelegramUser(chatID);
        TelegramDeliverer telegramDeliverer = telegramDelivererService.getDeliverByTelegramId(telegramUser.getId());
        telegramDeliverer.setDeliveryStatus(DeliveryStatus.EMPTY);
        telegramDelivererService.update(telegramDeliverer);

        CustomerOrder currentOrder = getCurrentOrder(telegramDeliverer.getId());
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

    private CustomerOrder getCurrentOrder(UUID telegramDelivereID) {
        List<CustomerOrder> processByDeliverer = customerOrderService.getOrdersInProcessByDeliverer(telegramDelivereID);
        return processByDeliverer.stream()
                .filter(customerOrder -> customerOrder.getOrderStatus().equals(OrderStatus.YOUR_ORDER_RECEIVED))
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
