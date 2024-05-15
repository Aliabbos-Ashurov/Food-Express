package com.pdp.telegram.repository.telegramUser;

import com.pdp.config.jsonFilePath.JsonFilePath;
import com.pdp.json.serializer.JsonSerializer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.web.repository.BaseRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Repository class for managing Telegram users.
 * Handles data storage and retrieval.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 14:43
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramUserRepository implements BaseRepository<TelegramUser, List<TelegramUser>> {
    private static volatile TelegramUserRepository instance;
    private static JsonSerializer<TelegramUser> jsonSerializer;

    /**
     * Returns the singleton instance of the repository.
     *
     * @return The singleton instance of the repository.
     */
    public static TelegramUserRepository getInstance() {
        if (instance == null) {
            synchronized (TelegramUserRepository.class) {
                if (instance == null) {
                    instance = new TelegramUserRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_TELEGRAM_USER));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a Telegram user to the repository.
     *
     * @param object The Telegram user to add.
     * @return True if the addition was successful, false otherwise.
     */
    @Override
    public boolean add(@NonNull TelegramUser object) {
        List<TelegramUser> load = load();
        load.add(object);
        save(load);
        return true;
    }

    /**
     * Removes a Telegram user from the repository.
     *
     * @param id The ID of the Telegram user to remove.
     * @return True if the removal was successful, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<TelegramUser> load = load();
        boolean b = load.removeIf(t -> Objects.equals(t.getId(), id));
        if (b) save(load);
        return b;
    }

    /**
     * Finds a Telegram user by its ID.
     *
     * @param id The ID of the Telegram user to find.
     * @return The Telegram user with the specified ID, or null if not found.
     */
    @Override
    public TelegramUser findById(@NonNull UUID id) {
        return load().stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all Telegram users from the repository.
     *
     * @return A list of all Telegram users.
     */
    @Override
    public List<TelegramUser> getAll() {
        return load();
    }

    /**
     * Loads Telegram users from storage.
     *
     * @return A list of loaded Telegram users.
     * @throws Exception If there's an error during deserialization.
     */
    @SneakyThrows
    @Override
    public List<TelegramUser> load() {
        return jsonSerializer.read(TelegramUser.class);
    }

    /**
     * Saves Telegram users to storage.
     *
     * @param telegramUsers The list of Telegram users to save.
     * @throws Exception If there's an error during serialization.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<TelegramUser> telegramUsers) {
        jsonSerializer.write(telegramUsers);
    }
}
