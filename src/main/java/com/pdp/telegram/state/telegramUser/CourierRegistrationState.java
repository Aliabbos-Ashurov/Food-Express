package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 14/May/2024  13:40
 **/
@Getter
public enum CourierRegistrationState implements State {
    ENTER_FULLNAME,
    ENTER_PHONE_NUMBER,
    ENTER_TRANSPORT_REGISTRATION_NUMBER;
}
