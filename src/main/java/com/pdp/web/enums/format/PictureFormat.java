package com.pdp.web.enums.format;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration representing different formats of pictures.
 * Each constant in this enum represents a specific picture format supported by the system.
 * <p>
 * This enum uses Lombok annotations to automatically generate a required-arguments constructor
 * and getters for accessing the format name of each picture format.
 *
 * @author Aliabbos Ashurov
 * @since 03/May/2024 13:02
 **/
@Getter
@RequiredArgsConstructor
public enum PictureFormat {
    JPEG("JPEG"),
    PNG("PNG"),
    SVG("SVG");

    private final String formatName;
}
