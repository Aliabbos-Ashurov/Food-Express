package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * Enum representing the different states for courier registration by Telegram users.
 * These states define the steps involved in registering as a courier.
 *
 * @author Aliabbos Ashurov
 * @since 14th May 2024, 13:40
 */
@Getter
public enum CourierRegistrationState implements State {
    ENTER_FULLNAME,
    ENTER_PHONE_NUMBER,
    ENTER_TRANSPORT_REGISTRATION_NUMBER
}
