package com.pdp.telegram.processor.message.user;

import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.state.DefaultState;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:05
 **/
public class DefaultMessageProcessor implements Processor<DefaultState> {
    @Override
    public void process(Update update, DefaultState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(DefaultState.SELECT_LANGUAGE)) {

        } else if (state.equals(DefaultState.BASE_USER_MENU)) {

        } else if (state.equals(DefaultState.BASE_DELIVERER_MENU)) {

        }
    }
}
