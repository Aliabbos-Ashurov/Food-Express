package com.pdp.utils.factory;

import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import lombok.NonNull;

/**
 * Factory class for generating AnswerCallbackQuery requests for Telegram bots.
 * This class simplifies the process of creating AnswerCallbackQuery instances.
 *
 * @author Aliabbos Ashurov
 * @since 14/May/2024  12:30
 */
public class AnswerCallBackQueryFactory {

    /**
     * Creates an AnswerCallbackQuery with a given message and shows an alert by default.
     *
     * @param callbackID The ID of the callback query to answer.
     * @param message    The message to be displayed in the callback answer.
     * @return An AnswerCallbackQuery request configured with the specified message and alert option.
     */
    public static AnswerCallbackQuery answerCallbackQuery(@NonNull String callbackID, @NonNull String message) {
        return answerCallbackQuery(callbackID, message, true);
    }

    /**
     * Creates an AnswerCallbackQuery with a given message and an option to show an alert.
     *
     * @param callbackID The ID of the callback query to answer.
     * @param message    The message to be displayed in the callback answer.
     * @param showAlert  Whether to show an alert to the user.
     * @return An AnswerCallbackQuery request configured with the specified message and alert option.
     */
    public static AnswerCallbackQuery answerCallbackQuery(@NonNull String callbackID, @NonNull String message, boolean showAlert) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(callbackID);
        answerCallbackQuery.text(message);
        answerCallbackQuery.showAlert(showAlert);
        return answerCallbackQuery;
    }
}
