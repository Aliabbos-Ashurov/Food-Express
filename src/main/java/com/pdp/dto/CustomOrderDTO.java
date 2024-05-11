package com.pdp.dto;

import java.util.UUID;

/**
 * Represents a data transfer object (DTO) for custom order details.
 *
 * This DTO encapsulates information needed to create or retrieve custom orders.
 *
 * @author Aliabbos Ashurov
 * @since 10/May/2024 16:25
 */
public record CustomOrderDTO(UUID userID,UUID branchID) {}