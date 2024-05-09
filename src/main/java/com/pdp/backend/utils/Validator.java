package com.pdp.backend.utils;

/**
 * Utility class providing validation methods.
 *
 * @author Aliabbos Ashurov
 * @since 09/May/2024 15:51
 */
public class Validator {

    /**
     * Checks if the given string contains, is equal to (case-insensitive),
     * or starts with the specified query string.
     *
     * @param string The string to be validated.
     * @param query  The query string to match against the string.
     * @return {@code true} if the string matches the query (case-insensitively); {@code false} otherwise.
     */
    public static boolean isValid(String string, String query) {
        return string.contains(query) || string.equalsIgnoreCase(query) || string.toUpperCase().equals(query.toUpperCase());
    }
}
