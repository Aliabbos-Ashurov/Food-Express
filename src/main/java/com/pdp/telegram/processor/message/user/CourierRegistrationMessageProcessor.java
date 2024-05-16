package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramDeliverer.TelegramDeliverer;
import com.pdp.telegram.model.telegramTransport.TelegramTransport;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramDeliverer.TelegramDelivererService;
import com.pdp.telegram.service.telegramTransport.TelegramTransportService;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.telegramUser.CourierRegistrationState;

import static com.pdp.telegram.state.telegramUser.CourierRegistrationState.*;

import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pdp.web.enums.role.Role;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.NonNull;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:11
 **/
public class CourierRegistrationMessageProcessor implements Processor<CourierRegistrationState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    private final TelegramDelivererService telegramDelivererService = ThreadSafeBeansContainer.telegramDelivererServiceThreadLocal.get();
    private final TelegramTransportService telegramTransportService = ThreadSafeBeansContainer.telegramTransportServiceThreadLocal.get();

    @Override
    public void process(Update update, CourierRegistrationState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(ENTER_PHONE_NUMBER)) {
            handleEnterPhoneNumber(chatID, message.contact());
        } else if (state.equals(ENTER_TRANSPORT_NAME)) {
            handleEnterTransportName(chatID, message.text());
        } else if (state.equals(ENTER_TRANSPORT_REGISTRATION_NUMBER)) {
            handleEnterTransportRegistrationNumber(chatID, message.text());
        }
    }

    private void handleEnterPhoneNumber(Long chatID, Contact contact) {
        if (Objects.isNull(contact)) {
            invalidSelectionSender(chatID);
            return;
        }
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(ENTER_TRANSPORT_NAME);
        telegramUser.setPhoneNumber(contact.phoneNumber());
        telegramUserService.update(telegramUser);
        bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("enter.transport.name", getTelegramUserLanguage(chatID))));
    }

    private void handleEnterTransportName(Long chatID, String transportName) {
        if (!validateTransportName(transportName)) {
            invalidTransportNameSender(chatID);
            return;
        }
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(ENTER_TRANSPORT_REGISTRATION_NUMBER);
        telegramUserService.update(telegramUser);
        buildDelivererTransportName(chatID, transportName);
        bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("enter.transport.registered.num", getTelegramUserLanguage(chatID))));
    }

    private void handleEnterTransportRegistrationNumber(Long chatID, String registeredNum) {
        if (!registeredNumberMatcher(registeredNum)) {
            invalidRegisterdNumSender(chatID);
            return;
        }
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setRole(Role.DELIVERER);
        telegramUser.setState(DefaultState.BASE_DELIVERER_MENU);
        telegramUserService.update(telegramUser);
        buildDelivererTransportNum(chatID, registeredNum);
        bot.execute(SendMessageFactory.sendMessageDeliverMenu(chatID, getTelegramUserLanguage(chatID)));
    }

    private static boolean registeredNumberMatcher(String message) {
        Pattern pattern = Pattern.compile("^(\\\\d{2}[a-zA-Z]\\\\d{3}[a-zA-Z]{2}|\\\\d{5}[a-zA-Z]{3})$");
        return pattern.matcher(message).matches();
    }

    private static boolean validateTransportName(String input) {
        String regex = "^[a-zA-Z0-9]{4,30}$";
        return input.matches(regex);
    }

    private void buildDelivererTransportName(@NonNull Long chatID, String transportName) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        TelegramDeliverer telegramDeliverer = telegramDelivererService.getDeliverByTelegramId(telegramUser.getId());
        TelegramTransport telegramTransport = TelegramTransport.builder()
                .name(transportName)
                .telegramDelivererID(telegramDeliverer.getId())
                .build();
        telegramTransportService.add(telegramTransport);
    }

    private void buildDelivererTransportNum(@NonNull Long chatID, String registeredNum) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        TelegramDeliverer telegramDeliverer = telegramDelivererService.getDeliverByTelegramId(telegramUser.getId());
        TelegramTransport telegramTransport = telegramTransportService.getTransportByDeliverID(telegramDeliverer.getId());
        telegramTransport.setRegisteredNumber(registeredNum);
        telegramTransportService.update(telegramTransport);
    }

    private void invalidSelectionSender(Long chatID) {
        bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("error.invalidSelection", getTelegramUserLanguage(chatID))));
    }

    private void invalidRegisterdNumSender(Long chatID) {
        bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("alert.invalid.registered.num", getTelegramUserLanguage(chatID))));
    }

    private void invalidTransportNameSender(Long chatID) {
        bot.execute(new SendMessage(chatID, MessageSourceUtils.getLocalizedMessage("alert.invalid.transport.name", getTelegramUserLanguage(chatID))));
    }

    private TelegramUser getTelegramUser(Long chatID) {
        return telegramUserService.findByChatID(chatID);
    }

    private Language getTelegramUserLanguage(Long chatID) {
        return getTelegramUser(chatID).getLanguage();
    }
}
