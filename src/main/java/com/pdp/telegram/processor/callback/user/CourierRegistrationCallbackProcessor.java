package com.pdp.telegram.processor.callback.user;

import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.state.telegramUser.CourierRegistrationState;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:11
 **/
public class CourierRegistrationCallbackProcessor implements Processor<CourierRegistrationState> {
    @Override
    public void process(Update update, CourierRegistrationState state) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = callbackQuery.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(CourierRegistrationState.ENTER_FULLNAME)){

        } else if (state.equals(CourierRegistrationState.ENTER_PHONE_NUMBER)) {

        } else if (state.equals(CourierRegistrationState.ENTER_TRANSPORT_REGISTRATION_NUMBER)) {

        }
    }
}
