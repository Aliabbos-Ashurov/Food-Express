package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  13:35
 **/
@Getter
public enum UserViewState implements State {
    VIEW_BRANDS,
    VIEW_CATEGORIES,
    VIEW_FOODS,
    COUNT
}
