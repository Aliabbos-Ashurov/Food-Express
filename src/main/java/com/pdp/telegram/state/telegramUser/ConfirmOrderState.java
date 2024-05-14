package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  13:41
 **/
@Getter
public enum ConfirmOrderState implements State {
    REQUEST_PHONE_NUMBER_FROM_USER,
    REQUEST_LOCATION_FROM_USER
}
