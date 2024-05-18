package com.pdp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration representing different types of payment methods.
 * Each constant in this enum represents a specific payment method supported by the system.
 * <p>
 * This enum uses Lombok annotations to automatically generate a required-arguments constructor
 * and getters for accessing the display name of each payment type.
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 13:22
 **/
@Getter
@RequiredArgsConstructor
public enum PaymentType {
    CASH("Cash"),
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    PAYPAL("PayPal"),
    APPLE_PAY("Apple Pay"),
    GOOGLE_PAY("Google Pay"),
    BANK_TRANSFER("Bank Transfer");

    private final String displayName;
}
