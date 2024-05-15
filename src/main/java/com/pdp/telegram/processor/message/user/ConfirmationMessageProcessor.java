package com.pdp.telegram.processor.message.user;

import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.state.telegramUser.ConfirmationState;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:17
 **/
public class ConfirmationMessageProcessor implements Processor<ConfirmationState> {
    @Override
    public void process(Update update, ConfirmationState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(ConfirmationState.ACCEPT_ORDER_IN_CART)){

        } else if (state.equals(ConfirmationState.ACCEPT_CLEAR_CART)) {

        }
    }
}