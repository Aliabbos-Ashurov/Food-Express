package com.pdp.telegram.service.telegramUser;

import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.repository.telegramUser.TelegramUserRepository;
import com.pdp.telegram.state.State;
import com.pdp.enums.Language;
import com.pdp.web.service.BaseService;
import lombok.NonNull;

import java.util.List;

/**
 * This interface defines the service layer for managing Telegram users.
 * It extends the BaseService interface.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 15:16
 */
public interface TelegramUserService extends BaseService<TelegramUser, List<TelegramUser>> {
    TelegramUserRepository repository = TelegramUserRepository.getInstance();

    /**
     * Finds the language associated with a given chat ID.
     *
     * @param chatID The ID of the chat.
     * @return The language associated with the chat ID.
     */
    Language findLanguageByChatID(@NonNull Long chatID);

    /**
     * Finds a Telegram user by their chat ID.
     *
     * @param chatID The chat ID of the Telegram user.
     * @return The Telegram user corresponding to the chat ID.
     */
    TelegramUser findByChatID(@NonNull Long chatID);

    List<TelegramUser> getTelegramUserByState(@NonNull State state);
}
