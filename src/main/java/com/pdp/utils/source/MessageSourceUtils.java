package com.pdp.utils.source;

import com.pdp.enums.Language;
import lombok.NonNull;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utility class that provides access to localized messages from ResourceBundle based
 * on the specified language. It supports different languages (English and Uzbek in this case)
 * by maintaining a separate ResourceBundle for each supported language.
 *
 * The language-specific ResourceBundle is encapsulated within a ThreadLocal variable
 * ensuring thread safety when accessed by multiple threads simultaneously.
 *
 * This class is typically used for internationalization (i18n) by retrieving language-specific
 * strings, which are key-driven, from properties files.
 *
 * @author Aliabbos Ashurov
 * @since 07/May/2024 20:46
 */
public class MessageSourceUtils {
    private static final String BASE_NAME = "message";
    private static final ThreadLocal<ResourceBundle> ENGLISH_BUNDLE = ThreadLocal.withInitial(() -> ResourceBundle.getBundle(BASE_NAME, Locale.forLanguageTag("en")));
    private static final ThreadLocal<ResourceBundle> UZBEK_BUNDLE = ThreadLocal.withInitial(() -> ResourceBundle.getBundle(BASE_NAME, Locale.forLanguageTag("uz")));


    /**
     * Returns a localized message string from the resource bundle, given the message key
     * and the language. If the language is not supported, a default message bundle is used.
     *
     * @param key      Key for the desired message in the ResourceBundle
     * @param language Language for the ResourceBundle
     * @return Localized string corresponding to the message key and language provided
     */
    public static String getLocalizedMessage(@NonNull String key, Language language) {
        ResourceBundle bundle;
        switch (language) {
            case UZ -> bundle = UZBEK_BUNDLE.get();
            case EN -> bundle = ENGLISH_BUNDLE.get();
            default -> bundle = ENGLISH_BUNDLE.get();
        }
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "[Message not found]";
        }
    }
}
