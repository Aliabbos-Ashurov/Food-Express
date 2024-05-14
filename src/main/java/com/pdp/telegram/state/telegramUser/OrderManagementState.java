package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  13:34
 **/
@Getter
public enum OrderManagementState implements State {
    CONFIRM_ORDER_ACCEPTANCE,
    CLEAR_CART_CONFIRMATION
}
