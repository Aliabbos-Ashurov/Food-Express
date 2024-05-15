package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * Enum representing the different states for managing orders by Telegram users.
 * These states define the actions involved in managing orders.
 *
 * @author Aliabbos Ashurov
 * @since 14th May 2024, 13:34
 */
@Getter
public enum OrderManagementState implements State {
    CONFIRM_ORDER_ACCEPTANCE,
    CLEAR_CART_CONFIRMATION
}
