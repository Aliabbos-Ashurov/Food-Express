package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.customerOrderGeoPoint.CustomerOrderGeoPoint;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.customerOrderGeoPiont.CustomerOrderGeoPointService;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.State;
import com.pdp.telegram.state.telegramUser.ConfirmOrderState;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.enums.Language;
import com.pdp.enums.OrderStatus;
import com.pdp.enums.PaymentType;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pdp.web.service.order.OrderService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Location;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;


public class ConfirmOrderMessageProcessor implements Processor<ConfirmOrderState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private final CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();
    private final CustomerOrderGeoPointService customerOrderGeoPointService = ThreadSafeBeansContainer.geoPointServiceThreadLocal.get();
    private final OrderService orderService = ThreadSafeBeansContainer.orderServiceThreadLocal.get();

    @Override
    public void process(Update update, ConfirmOrderState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        switch (state) {
            case REQUEST_PAYMENT_TYPE -> processPaymentType(message, chatID);
            case REQUEST_PHONE_NUMBER_FROM_USER -> processRequestPhoneNumber(message, chatID);
            case REQUEST_LOCATION_FROM_USER -> processRequestLocation(message, chatID);
        }
    }

    private void processPaymentType(Message message, Long chatID) {
        String text = message.text();
        for (PaymentType value : PaymentType.values()) {
            if (Objects.equals(value, PaymentType.valueOf(text))) {
                CustomerOrder notConfirmedOrder = customerOrderService.getNotConfirmedOrder(getTelegramUser(chatID).getId());
                notConfirmedOrder.setPaymentType(value);
                customerOrderService.update(notConfirmedOrder);
                updateTelegramUserState(chatID, ConfirmOrderState.REQUEST_PHONE_NUMBER_FROM_USER);
                bot.execute(SendMessageFactory.sendMessageContact(chatID, getTelegramUserLanguage(chatID)));
                return;
            }
        }
        invalidSelectionSender(chatID);
    }

    private void processRequestPhoneNumber(Message message, Long chatID) {
        Contact contact = message.contact();
        if (contact == null) {
            invalidSelectionSender(chatID);
            return;
        }
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setPhoneNumber(contact.phoneNumber());

        updateTelegramUserState(chatID, ConfirmOrderState.REQUEST_LOCATION_FROM_USER);
        bot.execute(SendMessageFactory.sendMessageLocation(chatID, getTelegramUserLanguage(chatID)));
    }

    private void processRequestLocation(Message message, Long chatID) {
        Location location = message.location();
        if (location == null) {
            invalidSelectionSender(chatID);
            return;
        }
        TelegramUser telegramUser = getTelegramUser(chatID);
        Float longitude = location.longitude();
        Float latitude = location.latitude();
        CustomerOrder customerOrder = customerOrderService.getNotConfirmedOrder(telegramUser.getId());
        CustomerOrderGeoPoint customerOrderGeoPoint = new CustomerOrderGeoPoint(latitude, longitude);
        customerOrderGeoPointService.add(customerOrderGeoPoint);
        System.out.println(customerOrderGeoPoint.getId());
        customerOrder.setCustomerOrderGeoPointID(customerOrderGeoPoint.getId());
        customerOrder.setOrderStatus(OrderStatus.LOOKING_FOR_A_DELIVERER);
        customerOrderService.update(customerOrder);
        updateTelegramUserState(chatID, DefaultState.BASE_USER_MENU);
        String messageText = MessageSourceUtils.getLocalizedMessage("alert.order.received", getTelegramUserLanguage(chatID));
        bot.execute(new SendMessage(chatID, messageText));
        List<SendMessage> sendMessages = SendMessageFactory.sendMessageNewOrderToDeliverer(customerOrder, getTelegramUserLanguage(chatID));
        sendMessages.forEach(bot::execute);
        bot.execute(SendMessageFactory.sendMessageWithUserMenu(chatID, getTelegramUserLanguage(chatID)));
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

    private void invalidSelectionSender(Long chatID) {
        bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("error.invalidSelection", getTelegramUserLanguage(chatID))));
    }
}
