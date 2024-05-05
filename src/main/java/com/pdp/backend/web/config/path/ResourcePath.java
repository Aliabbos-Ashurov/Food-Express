package com.pdp.backend.web.config.path;

import lombok.Getter;

/**
 * Defines the paths to various JSON resource files within the 'db' directory.
 * This class contains static constants that represent the file names and
 * locations of different data entities used throughout the application.
 *
 * The purpose of centralizing these paths is to provide a singular point
 * of reference for file locations, making it easier to manage and maintain
 * path strings across the codebase.
 *
 * Each constant is a combination of a directory path plus the specific file name.
 * For instance, the `ADDRESS` constant provides the complete path to the 'address.json' file.
 *
 * Usage of these path constants ensures consistency and avoids hard-coded strings
 * scattered throughout the application when referencing data files.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 14:56
 */
@Getter
public class ResourcePath {
    private static final String DIRECTORY = "db/";
    public static final String ADDRESS = DIRECTORY + "address.json";
    public static final String BANNED = DIRECTORY + "banned.json";
    public static final String BRANCH = DIRECTORY + "branch.json";
    public static final String BRAND = DIRECTORY + "brand.json";
    public static final String CATEGORY = DIRECTORY + "category.json";
    public static final String COMMENT = DIRECTORY + "comment.json";
    public static final String CUSTOMER_ORDER = DIRECTORY + "customerOrder.json";
    public static final String DELIVERER = DIRECTORY + "deliverer.json";
    public static final String DESCRIPTION = DIRECTORY + "description.json";
    public static final String DISCOUNT = DIRECTORY + "discount.json";
    public static final String FOOD = DIRECTORY + "food.json";
    public static final String FOOD_BRAND_MAPPING = DIRECTORY + "foodBrandMapping.json";
    public static final String ORDER = DIRECTORY + "order.json";
    public static final String PICTURE = DIRECTORY + "picture.json";
    public static final String TRANSPORT = DIRECTORY + "transport.json";
    public static final String USER = DIRECTORY + "user.json";
}
