package com.pdp.telegram.processor;

import com.pdp.telegram.state.State;
import com.pengrad.telegrambot.model.Update;

/**
 * Interface for processing Telegram updates.
 * Implementing classes should define the logic for processing updates.
 *
 * @param <S> The type of state associated with the processor.
 * @author Doniyor Nishonov
 * @since 14th May 2024, 17:32
 */
public interface Processor<S extends State> {
    /**
     * Processes the incoming Telegram update with the given state.
     *
     * @param update The Telegram update to process.
     * @param state  The state associated with the update.
     */
    void process(Update update, S state);
}
