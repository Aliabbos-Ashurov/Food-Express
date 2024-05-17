package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * Enum representing the different states for viewing orders by Telegram users.
 * These states define the types of orders that can be viewed.
 *
 * @author Aliabbos Ashurov
 * @since 14th May 2024, 13:29
 */
@Getter
public enum MyOrderState implements State {
    VIEW_ACTIVE_ORDERS_USER,
    VIEW_ARCHIVED_ORDERS
}
