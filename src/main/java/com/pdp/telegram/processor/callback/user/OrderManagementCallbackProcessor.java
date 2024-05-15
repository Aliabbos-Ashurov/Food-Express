package com.pdp.telegram.processor.callback.user;

import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.state.telegramUser.OrderManagementState;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:09
 **/
public class OrderManagementCallbackProcessor implements Processor<OrderManagementState> {
    @Override
    public void process(Update update, OrderManagementState state) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = callbackQuery.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(OrderManagementState.CONFIRM_ORDER_ACCEPTANCE)){

        }else if (state.equals(OrderManagementState.CLEAR_CART_CONFIRMATION)){

        }
    }
}