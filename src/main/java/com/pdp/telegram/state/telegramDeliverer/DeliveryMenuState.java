package com.pdp.telegram.state.telegramDeliverer;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  13:52
 **/
@Getter
public enum DeliveryMenuState implements State {
    VIEW_ASSIGNED_ORDERS,
    VIEW_ACTIVE_ORDERS;
}
