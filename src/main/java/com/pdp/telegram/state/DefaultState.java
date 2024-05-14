package com.pdp.telegram.state;

import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  13:25
 **/
@Getter
public enum DefaultState implements State {
    SELECT_LANGUAGE,
    BASE_USER_MENU,
    BASE_DELIVERER_MENU,
}
