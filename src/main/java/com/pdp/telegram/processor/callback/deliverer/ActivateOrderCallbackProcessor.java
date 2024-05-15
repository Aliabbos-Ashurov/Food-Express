package com.pdp.telegram.processor.callback.deliverer;

import com.pdp.telegram.processor.Processor;
import com.pdp.telegram.state.telegramDeliverer.ActiveOrderManagementState;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  18:19
 **/
public class ActivateOrderCallbackProcessor implements Processor<ActiveOrderManagementState> {
    @Override
    public void process(Update update, ActiveOrderManagementState state) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = callbackQuery.message();
        User user = message.from();
        Long chatID = user.id();
        if (state.equals(ActiveOrderManagementState.CONFIRM_ORDER_PICKUP)){

        } else if (state.equals(ActiveOrderManagementState.CONFIRM_ORDER_DELIVERY)) {

        } else if (state.equals(ActiveOrderManagementState.DELIVERY_FAILED)) {

        }
    }
}
