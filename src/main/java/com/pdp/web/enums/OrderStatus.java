package com.pdp.web.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration representing different statuses of an order during transportation.
 * Each constant in this enum represents a specific stage or status of an order in a delivery process.
 * <p>
 * This enum uses Lombok annotations to automatically generate getters and a required-arguments constructor
 * for initializing the description of each order status.
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 13:25
 **/
@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    NOT_CONFIRMED("NOT_CONFIRMED"),
    LOOKING_FOR_A_DELIVERER("LOOKING_FOR_A_DELIVERER"),
    YOUR_ORDER_RECEIVED("YOUR_ORDER_RECEIVED"),
    PROCESSING("PROCESSING"),
    IN_TRANSIT("IN_TRANSIT"),
    DELIVERED("DELIVERED"),
    FAILED_DELIVERY("FAILED_DELIVERY"),
    RETURNED("RETURNED");

    private final String description;
}
