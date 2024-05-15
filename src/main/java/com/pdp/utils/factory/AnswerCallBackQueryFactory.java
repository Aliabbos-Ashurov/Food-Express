package com.pdp.utils.factory;

import com.pengrad.telegrambot.request.AnswerCallbackQuery;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  12:30
 **/
public class AnswerCallBackQueryFactory {
    public static AnswerCallbackQuery answerCallbackQuery(String callbackID, String message) {
        return answerCallbackQuery(callbackID, message, true);
    }

    public static AnswerCallbackQuery answerCallbackQuery(String callbackID, String message, boolean showAlert) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(callbackID);
        answerCallbackQuery.text(message);
        answerCallbackQuery.showAlert(showAlert);
        return answerCallbackQuery;
    }
}
