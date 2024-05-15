package com.pdp.telegram.processor.callback.deliverer;

import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.state.telegramDeliverer.DeliveryMenuState;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:18
 **/
public class DeliveryMenuCallbackProcessor implements Processor<DeliveryMenuState> {
    @Override
    public void process(Update update, DeliveryMenuState state) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = callbackQuery.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(DeliveryMenuState.VIEW_ASSIGNED_ORDERS)) {

        } else if (state.equals(DeliveryMenuState.VIEW_ACTIVE_ORDERS)) {

        }
    }
}