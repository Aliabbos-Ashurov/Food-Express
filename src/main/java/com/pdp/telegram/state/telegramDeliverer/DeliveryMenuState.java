package com.pdp.telegram.state.telegramDeliverer;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * Enum representing the different states for the delivery menu of Telegram deliverers.
 * These states define the options available in the deliverer's menu.
 *
 * @author Aliabbos Ashurov
 * @since 14th May 2024, 13:52
 */
@Getter
public enum DeliveryMenuState implements State {
    DEFAULT_DELIVERY_MENU,
    VIEW_ASSIGNED_ORDERS,
    VIEW_ACTIVE_ORDERS
}
