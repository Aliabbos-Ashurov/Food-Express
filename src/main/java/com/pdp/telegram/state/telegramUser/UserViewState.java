package com.pdp.telegram.state.telegramUser;

import com.pdp.telegram.state.State;
import lombok.Getter;

/**
 * Enum representing the different view states for Telegram users.
 * These states define the user's view within the application.
 *
 * @author Aliabbos Ashurov
 * @since 14th May 2024, 13:35
 */
@Getter
public enum UserViewState implements State {
    VIEW_BRANDS,
    VIEW_CATEGORIES,
    VIEW_FOODS,
    COUNT
}
