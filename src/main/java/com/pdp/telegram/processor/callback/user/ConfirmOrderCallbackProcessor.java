package com.pdp.telegram.processor.callback.user;

import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.state.telegramUser.ConfirmOrderState;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:16
 **/
public class ConfirmOrderCallbackProcessor implements Processor<ConfirmOrderState> {
    @Override
    public void process(Update update, ConfirmOrderState state) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = callbackQuery.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(ConfirmOrderState.REQUEST_PHONE_NUMBER_FROM_USER)) {

        } else if (state.equals(ConfirmOrderState.REQUEST_LOCATION_FROM_USER)){

        }
    }
}
