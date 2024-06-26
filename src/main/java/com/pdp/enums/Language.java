package com.pdp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing supported languages within the system.
 *
 * <p>
 * Each language is associated with a specific type identifier (e.g., "uz", "ru", "en").
 * </p>
 *
 * @author Aliabbos Ashurov
 * @since 30/April/2024 21:00
 */
@Getter
@RequiredArgsConstructor
public enum Language {
    UZ("UZ"),
    RU("RU"),
    EN("EN");
    private final String type;
}
