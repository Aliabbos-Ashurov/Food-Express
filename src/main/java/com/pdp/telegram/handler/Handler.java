package com.pdp.telegram.handler;

import com.pengrad.telegrambot.model.Update;

/**
 * Interface for handling Telegram updates.
 * Implementing classes should define the logic for handling updates.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 15:59
 */
public interface Handler {
    /**
     * Handles the incoming Telegram update.
     *
     * @param update The Telegram update to handle.
     */
    void handle(Update update);
}
