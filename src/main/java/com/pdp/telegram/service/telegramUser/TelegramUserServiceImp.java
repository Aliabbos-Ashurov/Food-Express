package com.pdp.telegram.service.telegramUser;

import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.utils.Validator;
import com.pdp.web.enums.Language;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation class for managing Telegram users.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 15:19
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramUserServiceImp implements TelegramUserService {
    private static volatile TelegramUserServiceImp instance;

    /**
     * Returns the singleton instance of the service.
     *
     * @return The singleton instance of the service.
     */
    public static TelegramUserServiceImp getInstance() {
        if (instance == null) {
            synchronized (TelegramUserServiceImp.class) {
                if (instance == null) {
                    instance = new TelegramUserServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Finds a Telegram user by their chat ID.
     *
     * @param chatID The chat ID of the Telegram user.
     * @return The Telegram user corresponding to the chat ID, or null if not found.
     */
    @Override
    public TelegramUser findByChatID(@NonNull Long chatID) {
        return getAll().stream()
                .filter(t -> Objects.equals(t.getChatID(), chatID))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds the language associated with a given chat ID.
     *
     * @param chatID The ID of the chat.
     * @return The language associated with the chat ID, or null if not found.
     */
    @Override
    public Language findLanguageByChatID(@NonNull Long chatID) {
        return getAll().stream()
                .filter(t -> Objects.equals(t.getChatID(), chatID))
                .map(TelegramUser::getLanguage)
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a Telegram user if not already existing.
     *
     * @param telegramUser The Telegram user to add.
     * @return True if the addition was successful, false if the user already exists.
     */
    @Override
    public boolean add(@NonNull TelegramUser telegramUser) {
        boolean anyMatch = getAll().stream().anyMatch(t -> Objects.equals(t.getChatID(), telegramUser.getChatID()));
        if (anyMatch) return false;
        return repository.add(telegramUser);
    }

    /**
     * Removes a Telegram user by its ID.
     *
     * @param id The ID of the Telegram user to remove.
     * @return True if the removal was successful, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates a Telegram user's data.
     *
     * @param telegramUser The updated Telegram user.
     * @return True if the update was successful, false if the user was not found.
     */
    @Override
    public boolean update(@NonNull TelegramUser telegramUser) {
        List<TelegramUser> telegramUsers = getAll();
        Optional<TelegramUser> first = telegramUsers.stream()
                .filter(t -> Objects.equals(t.getChatID(), telegramUser.getChatID()))
                .findFirst();
        if (first.isPresent()) {
            updateTelegramUserData(first.get(), telegramUser);
            return true;
        }
        return false;
    }

    /**
     * Helper method to update the data of a Telegram user.
     *
     * @param telegramUser The Telegram user to update.
     * @param updated      The updated data of the Telegram user.
     */
    private void updateTelegramUserData(TelegramUser telegramUser, TelegramUser updated) {
        telegramUser.setChatID(updated.getChatID());
        telegramUser.setLanguage(updated.getLanguage());
        telegramUser.setFirstName(updated.getFirstName());
        telegramUser.setRole(updated.getRole());
        telegramUser.setState(updated.getState());
        telegramUser.setPhoneNumber(updated.getPhoneNumber());
        telegramUser.setUsername(updated.getUsername());
    }

    /**
     * Searches for Telegram users based on a query string.
     *
     * @param query The search query.
     * @return A list of Telegram users matching the query.
     */
    @Override
    public List<TelegramUser> search(@NonNull String query) {
        return getAll().stream()
                .filter(t -> Validator.isValid(t.getDisplayName(), query))
                .toList();
    }

    /**
     * Retrieves a Telegram user by its ID.
     *
     * @param id The ID of the Telegram user to retrieve.
     * @return The Telegram user with the specified ID.
     */

    @Override
    public TelegramUser getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all Telegram users.
     *
     * @return A list of all Telegram users.
     */
    @Override
    public List<TelegramUser> getAll() {
        return repository.getAll();
    }
}
