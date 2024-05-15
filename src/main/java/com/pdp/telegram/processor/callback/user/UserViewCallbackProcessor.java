package com.pdp.telegram.processor.callback.user;

import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.state.telegramUser.UserViewState;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:06
 **/
public class UserViewCallbackProcessor implements Processor<UserViewState> {
    @Override
    public void process(Update update, UserViewState state) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = callbackQuery.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(UserViewState.VIEW_BRANDS)) {

        } else if (state.equals(UserViewState.VIEW_CATEGORIES)) {

        } else if (state.equals(UserViewState.VIEW_FOODS)) {

        } else if (state.equals(UserViewState.COUNT)) {

        }
    }
}