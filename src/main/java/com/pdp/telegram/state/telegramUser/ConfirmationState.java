package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  13:48
 **/
@Getter
public enum ConfirmationState implements State {
    ACCEPT_ORDER_IN_CART,
    ACCEPT_CLEAR_CART;
}
