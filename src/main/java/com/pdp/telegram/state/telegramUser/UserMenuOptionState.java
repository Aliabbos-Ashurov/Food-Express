package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * Enum representing the different menu options for Telegram users.
 * These states define the available options in the user's menu.
 *
 * @author Aliabbos Ashurov
 * @since 14th May 2024, 13:27
 */
@Getter
public enum UserMenuOptionState implements State {
    PLACE_ORDER,
    VIEW_MY_ORDERS,
    REGISTER_AS_COURIER
}
