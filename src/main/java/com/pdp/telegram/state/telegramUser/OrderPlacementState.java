package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * Enum representing the different states for order placement by Telegram users.
 * These states define the steps involved in placing an order.
 *
 * @author Aliabbos Ashurov
 * @since 14th May 2024, 13:30
 */
@Getter
public enum OrderPlacementState implements State {
    SELECT_BRAND,
    VIEW_CART
}
