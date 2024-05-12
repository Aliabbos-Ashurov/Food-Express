package com.pdp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Aliabbos Ashurov
 * @since 11/May/2024  15:42
 **/
public class Utils {

    /**
     * Retrieves an element from a list based on the provided index.
     *
     * @param <T>    The type of elements in the list.
     * @param list   The list from which to retrieve the element.
     * @param choice The 1-based index of the element to retrieve.
     * @return The element at the specified index or null if the index is out of range.
     */
    public static <T> T getElementByIndex(List<T> list, int choice) {
        if (choice < 1 || choice > list.size()) return null;
        return list.get(choice - 1);
    }
    public static <T> T getElementByIndexForSet(Set<T> set, int choice) {
        if (choice < 1 || choice > set.size()) return null;
        return new ArrayList<>(set).get(choice - 1);
    }
}
