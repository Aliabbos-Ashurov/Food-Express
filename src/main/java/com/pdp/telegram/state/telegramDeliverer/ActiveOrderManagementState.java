package com.pdp.telegram.state.telegramDeliverer;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * Enum representing the different states for managing active orders by Telegram deliverers.
 * These states define the actions involved in managing active orders.
 *
 * @author Aliabbos Ashurov
 * @since 14th May 2024, 13:54
 */
@Getter
public enum ActiveOrderManagementState implements State {
    CONFIRM_ORDER_PICKUP,
    CONFIRM_ORDER_DELIVERY,
    DELIVERY_FAILED
}
