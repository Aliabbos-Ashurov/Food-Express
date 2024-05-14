package com.pdp.telegram.state.telegramDeliverer;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  13:54
 **/
@Getter
public enum ActiveOrderManagementState implements State {
    CONFIRM_ORDER_PICKUP,
    CONFIRM_ORDER_DELIVERY,
    DELIVERY_FAILED;
}
