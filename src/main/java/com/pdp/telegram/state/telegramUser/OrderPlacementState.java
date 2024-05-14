package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  13:30
 **/
@Getter
public enum OrderPlacementState implements State {
    SELECT_BRAND,
    VIEW_CART
}
