package com.pdp.telegram.processor.message.user;

import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.state.telegramUser.OrderPlacementState;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  19:52
 **/
public class OrderPlacementMessageProcessor implements Processor<OrderPlacementState> {
    @Override
    public void process(Update update, OrderPlacementState state) {
        Message message = update.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(OrderPlacementState.SELECT_BRAND)) {

        } else if (state.equals(OrderPlacementState.VIEW_CART)) {

        }
    }
}