package com.pdp.utils.source;

import com.pdp.enums.Language;
import com.pdp.enums.OrderStatus;
import lombok.NonNull;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Aliabbos Ashurov
 * @since 09/May/2024  11:48
 **/
public class StatusSourceUtils {
    private static final String BASE_NAME = "status";
    private static final ThreadLocal<ResourceBundle> ENGLISH_BUNDLE = ThreadLocal.withInitial(() -> ResourceBundle.getBundle(BASE_NAME, Locale.forLanguageTag("en")));
    private static final ThreadLocal<ResourceBundle> UZBEK_BUNDLE = ThreadLocal.withInitial(() -> ResourceBundle.getBundle(BASE_NAME, Locale.forLanguageTag("uz")));


    public static String getLocalizedStatus(@NonNull OrderStatus orderStatus, Language language) {
        ResourceBundle bundle;
        switch (language) {
            case UZ -> bundle = UZBEK_BUNDLE.get();
            case EN -> bundle = ENGLISH_BUNDLE.get();
            default -> bundle = ENGLISH_BUNDLE.get();
        }
        try {
            return bundle.getString(String.valueOf(orderStatus));
        } catch (MissingResourceException e) {
            return "[Message not found]";
        }
    }
}
