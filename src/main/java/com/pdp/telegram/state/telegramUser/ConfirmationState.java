package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * Enum representing the different confirmation states for actions by Telegram users.
 * These states define the options for confirming or accepting actions.
 *
 * @author Aliabbos Ashurov
 * @since 14th May 2024, 13:48
 */
@Getter
public enum ConfirmationState implements State {
    ACCEPT_ORDER_IN_CART,
    ACCEPT_CLEAR_CART
}
