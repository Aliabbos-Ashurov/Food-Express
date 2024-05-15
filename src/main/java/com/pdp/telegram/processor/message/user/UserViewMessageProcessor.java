package com.pdp.telegram.processor.message.user;

import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.state.telegramUser.UserViewState;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:06
 **/
public class UserViewMessageProcessor implements Processor<UserViewState> {
    @Override
    public void process(Update update, UserViewState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(UserViewState.VIEW_BRANDS)) {

        } else if (state.equals(UserViewState.VIEW_CATEGORIES)) {

        } else if (state.equals(UserViewState.VIEW_FOODS)) {

        } else if (state.equals(UserViewState.COUNT)) {

        }
    }
}