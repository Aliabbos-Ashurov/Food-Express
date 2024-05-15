package com.pdp.telegram.processor.callback.user;

import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.state.telegramUser.OrderPlacementState;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  19:52
 **/
public class OrderPlacementCallbackProcessor implements Processor<OrderPlacementState> {
    @Override
    public void process(Update update, OrderPlacementState state) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = callbackQuery.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(OrderPlacementState.SELECT_BRAND)) {

        } else if (state.equals(OrderPlacementState.VIEW_CART)) {

        }
    }
}
