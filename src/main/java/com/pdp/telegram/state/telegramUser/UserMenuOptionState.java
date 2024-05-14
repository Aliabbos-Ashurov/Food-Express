package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  13:27
 **/
@Getter
public enum UserMenuOptionState implements State {
    PLACE_ORDER,
    VIEW_MY_ORDERS,
    REGISTER_AS_COURIER;
}
