package com.pdp.telegram.state;

import lombok.Getter;

/**
 * Enum representing the default states for Telegram bot processing.
 * These states define the initial stages of interaction with the bot.
 *
 * @author Aliabbos Ashurov
 * @since 14th May 2024, 13:25
 */
@Getter
public enum DefaultState implements State {
    SELECT_LANGUAGE,
    BASE_USER_MENU,
    BASE_DELIVERER_MENU,
}
