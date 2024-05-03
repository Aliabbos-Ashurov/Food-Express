package com.pdp.backend.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import static com.pdp.backend.web.enums.Permission.*;
import java.util.Set;
/**
 * Enum representing different roles within the system and their associated permissions.
 *
 * <p>
 * Each role defines a set of permissions granted to users assigned to that role.
 * </p>
 *
 * @author Aliabbos Ashurov
 * @since 30/April/2024 20:31
 * @see Permission
 */
@Getter
@AllArgsConstructor
public enum Role {
    USER(Set.of(ORDER,MODIFY_SETTINGS,USE_BOT,CHANGE_NICKNAME,WRITE_COMMENT,READ_COMMENT)),
    DELIVERER(Set.of(TAKE_ORDER,VIEW_ORDER)),
    ADMIN(Set.of(MANAGE_USERS,MANAGE_ORDERS,MANAGE_DELIVERER,MANAGE_BRANDS,BLOCK_USERS_AND_DELIVERER,VIEW_FINANCES,VIEW_STATISTICS)),
    SUPERADMIN(Set.of(MANAGE_ROLES,VIEW_LOGS,EXPORT_DATA,IMPORT_DATA)),
    SUPPORT(null),
    DEVELOPER(null);
    private final Set<Permission> permissions;
}
