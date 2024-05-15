package com.pdp.telegram.processor.callback.user;

import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.state.telegramUser.UserMenuOptionState;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:07
 **/
public class UserMenuOptionCallbackProcessor implements Processor<UserMenuOptionState> {
    @Override
    public void process(Update update, UserMenuOptionState state) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = callbackQuery.message();
        User user = message.from();
        Long chatID = user.id();
        if(state.equals(UserMenuOptionState.PLACE_ORDER)){

        } else if (state.equals(UserMenuOptionState.VIEW_MY_ORDERS)) {

        } else if (state.equals(UserMenuOptionState.REGISTER_AS_COURIER)) {

        }
    }
}