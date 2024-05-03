package com.pdp.backend.web.enums;

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
    ORDER_RECEIVED("Order Received"),
    PROCESSING("Processing"),
    IN_TRANSIT("In Transit"),
    OUT_FOR_DELIVERY("Out for Delivery"),
    DELIVERED("Delivered"),
    FAILED_DELIVERY("Failed Delivery"),
    RETURNED("Returned");

    private final String description;
}
