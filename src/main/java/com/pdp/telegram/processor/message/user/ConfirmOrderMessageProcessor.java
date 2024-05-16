package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.customerOrderGeoPoint.CustomerOrderGeoPoint;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.customerOrderGeoPiont.CustomerOrderGeoPointService;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.telegramUser.ConfirmOrderState;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Location;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;


public class ConfirmOrderMessageProcessor implements Processor<ConfirmOrderState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private final CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();
    private final CustomerOrderGeoPointService customerOrderGeoPointService = ThreadSafeBeansContainer.geoPointServiceThreadLocal.get();

    @Override
    public void process(Update update, ConfirmOrderState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        TelegramUser telegramUser = telegramUserService.findByChatID(chatID);
        switch (state) {
            case REQUEST_PHONE_NUMBER_FROM_USER -> processRequestPhoneNumber(message, chatID, telegramUser);
            case REQUEST_LOCATION_FROM_USER -> processRequestLocation(message, chatID, telegramUser);
        }
    }

    private void processRequestPhoneNumber(Message message, Long chatID, TelegramUser telegramUser) {
        Contact contact = message.contact();
        if (contact == null) {
            invalidSelectionSender(chatID);
            return;
        }
        telegramUser.setPhoneNumber(contact.phoneNumber());
        telegramUser.setState(ConfirmOrderState.REQUEST_LOCATION_FROM_USER);
        telegramUserService.update(telegramUser);
        bot.execute(SendMessageFactory.sendMessageLocation(chatID, getTelegramUserLanguage(chatID)));
    }

    private void processRequestLocation(Message message, Long chatID, TelegramUser telegramUser) {
        Location location = message.location();
        if (location == null) {
            invalidSelectionSender(chatID);
            return;
        }
        Float longitude = location.longitude();
        Float latitude = location.latitude();
        CustomerOrder customerOrder = customerOrderService.getByID(telegramUser.getId());
        CustomerOrderGeoPoint customerOrderGeoPoint = new CustomerOrderGeoPoint(latitude, longitude);
        customerOrderGeoPointService.add(customerOrderGeoPoint);
        customerOrder.setCustomerOrderGeoPointID(customerOrderGeoPoint.getId());
        customerOrderService.update(customerOrder);
        telegramUser.setState(DefaultState.BASE_USER_MENU);
        String messageText = MessageSourceUtils.getLocalizedMessage("alert.order.received", getTelegramUserLanguage(chatID));
        bot.execute(new SendMessage(chatID, messageText));
        bot.execute(SendMessageFactory.sendMessageWithUserMenu(chatID, getTelegramUserLanguage(chatID)));
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
