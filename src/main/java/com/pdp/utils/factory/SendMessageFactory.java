package com.pdp.utils.factory;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.utils.source.MessageSourceUtils;
import com.pdp.web.enums.Language;
import com.pdp.web.model.brand.Brand;
import com.pdp.web.model.description.Description;
import com.pdp.web.service.brand.BrandService;
import com.pdp.web.service.description.DescriptionService;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  12:29
 **/
public class SendMessageFactory {
    public static SendMessage sendMessageSelectLanguageMenu(Object chatID) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.language", Language.EN);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.selectLangButtons());
    }

    public static SendMessage sendMessageWithUserMenu(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.choose", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.userButtons(language));
    }

    public static SendMessage sendMessageWithBrandsMenu(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.choose.brand", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.viewBrandsButtons(language));
    }

    public static SendMessage sendMessageDeliverMenu(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.choose", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.deliverButtons(language));
    }

    public static SendMessage sendMessageOrderPlacementMenu(Object chatID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.choose", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.orderPlacementButtons(language));

    }

    public static SendMessage sendMessageBrandCategoriesMenu(Object chatID, UUID brandID, Language language) {
        String message = MessageSourceUtils.getLocalizedMessage("alert.choose", language);
        return createMessage(chatID, message, ReplyKeyboardMarkupFactory.viewBrandCategoriesButtons(brandID, language));
    }

    public static SendMessage sendMessageUpdateOrder(Object chatID, UUID orderID, Language language) {
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkupFactory.foodCounter(orderID);
        String localizedMessage = MessageSourceUtils.getLocalizedMessage("alert.quantity", language);
        return createMessage(chatID, localizedMessage, keyboardMarkup);
    }

    public static SendMessage sendMessageUpdateOrderCount(Object chatID, UUID orderID, String callbackData, Language language) {
        String action = callbackData.substring(0, 1);
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkupFactory.updateFoodCount(orderID, action);
        String quantity = MessageSourceUtils.getLocalizedMessage("alert.quantity.update", language);
        return createMessage(chatID, quantity, keyboardMarkup);
    }

    public static SendMessage sendMessageConfirmation(Object chatID, Language language) {
        String alert = MessageSourceUtils.getLocalizedMessage("alert.choose", language);
        return createMessage(chatID, alert, ReplyKeyboardMarkupFactory.confirmationButtons(language));

    }

    private static SendMessage createMessage(Object chatID, String messageText, Keyboard keyboardMarkup) {
        SendMessage message = new SendMessage(chatID, messageText);
        return message.replyMarkup(keyboardMarkup);
    }

}
