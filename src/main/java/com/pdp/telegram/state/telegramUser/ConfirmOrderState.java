package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * Enum representing the different states for confirming orders by Telegram users.
 * These states define the steps involved in confirming an order.
 *
 * @author Aliabbos Ashurov
 * @since 14th May 2024, 13:41
 */
@Getter
public enum ConfirmOrderState implements State {
    REQUEST_PAYMENT_TYPE,
    REQUEST_PHONE_NUMBER_FROM_USER,
    REQUEST_LOCATION_FROM_USER
}
