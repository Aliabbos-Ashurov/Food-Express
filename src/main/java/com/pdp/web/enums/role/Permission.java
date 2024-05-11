package com.pdp.web.enums.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing different permissions within the system.
 * Each permission grants specific access rights to different user roles.
 *
 * <p>
 * Permissions are categorized based on the roles they are associated with:
 * - DELIVERER: Permissions related to delivery operations.
 * - USER: Permissions available to regular users.
 * - ADMIN: Permissions granted to administrators.
 * - SUPER_ADMIN: Permissions reserved for super administrators.
 * </p>
 *
 * @author Aliabbos Ashurov
 * @since 30/April/2024 20:31
 */
@Getter
@RequiredArgsConstructor
public enum Permission {
    // DELIVERER:
    TAKE_ORDER("TAKE_ORDER"),
    VIEW_ORDER("VIEW_ORDER"),

    // USER:
    WRITE_COMMENT("WRITE_COMMENT"),
    READ_COMMENT("READ_COMMENT"),
    MODIFY_SETTINGS("MODIFY_SETTINGS"),
    ORDER("ORDER"),
    CHANGE_NICKNAME("CHANGE_NICKNAME"),
    USE_BOT("USE_BOT"),

    // ADMIN:
    VIEW_STATISTICS("VIEW_STATISTICS"),
    BLOCK_USERS_AND_DELIVERER("BLOCK_USERS_AND_DELIVERER"),
    MANAGE_USERS("MANAGE_USERS"),
    MANAGE_DELIVERER("MANAGE_DELIVERER"),
    MANAGE_BRANDS("MANAGE_BRANDS"),
    MANAGE_ORDERS("MANAGE_ORDERS"),
    VIEW_FINANCES("VIEW_FINANCES"),

    // SUPER_ADMIN:
    EXPORT_DATA("EXPORT_DATA"),
    IMPORT_DATA("IMPORT_DATA"),
    MANAGE_ROLES("MANAGE_ROLES"),
    VIEW_LOGS("VIEW_LOGS");
    private final String access;
}
