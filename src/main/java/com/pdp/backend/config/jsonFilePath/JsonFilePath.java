package com.pdp.backend.config.jsonFilePath;

/**
 * Defines the paths to various JSON resource files within the 'db' directory.
 * This class contains static constants that represent the file names and
 * locations of different data entities used throughout the application.
 * <p>
 * The purpose of centralizing these paths is to provide a singular point
 * of reference for file locations, making it easier to manage and maintain
 * path strings across the codebase.
 * <p>
 * Each constant is a combination of a directory path plus the specific file name.
 * For instance, the `ADDRESS` constant provides the complete path to the 'address.json' file.
 * <p>
 * Usage of these path constants ensures consistency and avoids hard-coded strings
 * scattered throughout the application when referencing data files.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 14:56
 */
public interface JsonFilePath extends DirectoryPath {
    String PATH_ADDRESS = DIRECTORY + "address.json";
    String PATH_BANNED = DIRECTORY + "banned.json";
    String PATH_BRANCH = DIRECTORY + "branch.json";
    String PATH_BRAND = DIRECTORY + "brand.json";
    String PATH_CATEGORY = DIRECTORY + "category.json";
    String PATH_COMMENT = DIRECTORY + "comment.json";
    String PATH_CUSTOMER_ORDER = DIRECTORY + "customerOrder.json";
    String PATH_DELIVERER = DIRECTORY + "deliverer.json";
    String PATH_DESCRIPTION = DIRECTORY + "description.json";
    String PATH_DISCOUNT = DIRECTORY + "discount.json";
    String PATH_FOOD = DIRECTORY + "food.json";
    String PATH_FOOD_BRAND_MAPPING = DIRECTORY + "foodBrandMapping.json";
    String PATH_ORDER = DIRECTORY + "order.json";
    String PATH_PICTURE = DIRECTORY + "picture.json";
    String PATH_TRANSPORT = DIRECTORY + "transport.json";
    String PATH_USER = DIRECTORY + "user.json";
    String PATH_BRANCH_LOCATION = DIRECTORY + "branchLocation.json";
}
