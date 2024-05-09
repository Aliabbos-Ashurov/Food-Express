package com.pdp.backend.utils;

/**
 * Utility class providing validation methods.
 *
 * @author Aliabbos Ashurov
 * @since 09/May/2024 15:51
 */
public class Validator {

    /**
     * Checks if the given string contains the query, is equal to it (ignoring case), or starts with the query (ignoring case).
     *
     * @param string The string to be validated against the query.
     * @param query The query that is being checked for within the string.
     * @return {@code true} if the string matches the query according to the conditions, {@code false} otherwise.
     */
    public static boolean isValid(String string,String query) {
        return string.contains(query) || string.equalsIgnoreCase(query) || string.toUpperCase().equals(query.toUpperCase());
    }
}
