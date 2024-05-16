package com.pdp.telegram.processor.message.user;

import com.pdp.config.TelegramBotConfiguration;
import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.service.telegramUser.TelegramUserService;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.telegramUser.ConfirmOrderState;
import com.pdp.telegram.state.telegramUser.OrderPlacementState;
import com.pdp.telegram.state.telegramUser.UserMenuOptionState;
import com.pdp.telegram.state.telegramUser.UserViewState;
import com.pdp.utils.factory.SendMessageFactory;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pdp.web.model.brand.Brand;
import com.pdp.web.service.brand.BrandService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import static com.pdp.telegram.state.telegramUser.OrderPlacementState.*;

import java.util.Objects;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  19:52
 **/
public class OrderPlacementMessageProcessor implements Processor<OrderPlacementState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();
    private final TelegramUserService telegramUserService = ThreadSafeBeansContainer.telegramUserServiceThreadLocal.get();
    BrandService brandService = ThreadSafeBeansContainer.brandServiceThreadLocal.get();

    @Override
    public void process(Update update, OrderPlacementState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        TelegramUser telegramUser = telegramUserService.findByChatID(chatID);
        String text = message.text();
        switch (state) {
            case SELECT_BRAND -> handleSelectBrand(text, chatID, telegramUser);
            case VIEW_CART -> handleViewCart(text, chatID, telegramUser);
        }
    }


    private void handleSelectBrand(String text, Long chatID, TelegramUser telegramUser) {
        if (checkLocalizedMessage(text, "button.back", chatID)) handleBackToMain(chatID);
        else {
            Brand brand = brandService.getBrandByName(text);
            if (Objects.isNull(brand)) {
                invalidSelectionSender(chatID);
                return;
            }
            telegramUser.setState(UserViewState.VIEW_CATEGORIES);
            telegramUserService.update(telegramUser);
            bot.execute(SendMessageFactory.sendMessageBrandCategoriesMenu(chatID, brand.getId(), getTelegramUserLanguage(chatID)));
        }
    }

    private void handleViewCart(String text, Long chatID, TelegramUser telegramUser) {
        if (checkLocalizedMessage(text, "button.back", chatID)) handleBackToMain(chatID);
        else if (checkLocalizedMessage(text, "alert.make.order", chatID)) {
            telegramUser.setState(ConfirmOrderState.REQUEST_PHONE_NUMBER_FROM_USER);
            telegramUserService.update(telegramUser);
            bot.execute(SendMessageFactory.sendMessageContact(chatID, getTelegramUserLanguage(chatID)));
        } else invalidSelectionSender(chatID);
    }


    private void handleBackToMain(Long chatID) {
        TelegramUser telegramUser = getTelegramUser(chatID);
        telegramUser.setState(UserMenuOptionState.PLACE_ORDER);
        telegramUserService.update(telegramUser);
        bot.execute(SendMessageFactory.sendMessageOrderPlacementMenu(chatID, getTelegramUserLanguage(chatID)));
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
