package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  13:29
 **/
@Getter
public enum MyOrderState implements State {
    VIEW_ACTIVE_ORDERS,
    VIEW_ARCHIVED_ORDERS
}
