package com.pdp.telegram.processor.message.user;

import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.state.telegramUser.MyOrderState;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:09
 **/
public class MyOrderMessageProcessor implements Processor<MyOrderState> {
    @Override
    public void process(Update update, MyOrderState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(MyOrderState.VIEW_ACTIVE_ORDERS)){

        } else if (state.equals(MyOrderState.VIEW_ARCHIVED_ORDERS)) {

        }
    }
}