package com.pdp.telegram.handler;

import com.pengrad.telegrambot.model.Update;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  15:59
 **/
public interface Handler {
    void handle(Update update);
}
