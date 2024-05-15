package com.pdp.telegram.repository.telegramDeliverer;

import com.pdp.json.serializer.JsonSerializer;
import com.pdp.telegram.model.telegramDeliverer.TelegramDeliverer;
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
 * Repository class for managing Telegram deliverers.
 * Handles data storage and retrieval.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 14:39
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramDelivererRepository implements BaseRepository<TelegramDeliverer, List<TelegramDeliverer>> {
    private static volatile TelegramDelivererRepository instance;
    private static JsonSerializer<TelegramDeliverer> jsonSerializer;

    /**
     * Returns the singleton instance of the repository.
     *
     * @return The singleton instance of the repository.
     */
    public static TelegramDelivererRepository getInstance() {
        if (instance == null) {
            synchronized (TelegramDelivererRepository.class) {
                if (instance == null) {
                    instance = new TelegramDelivererRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_TELEGRAM_DELIVERER));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a Telegram deliverer to the repository.
     *
     * @param object The Telegram deliverer to add.
     * @return True if the addition was successful, false otherwise.
     */
    @Override
    public boolean add(@NonNull TelegramDeliverer object) {
        List<TelegramDeliverer> load = load();
        load.add(object);
        save(load);
        return true;
    }

    /**
     * Removes a Telegram deliverer from the repository.
     *
     * @param id The ID of the Telegram deliverer to remove.
     * @return True if the removal was successful, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<TelegramDeliverer> load = load();
        boolean b = load.removeIf(t -> Objects.equals(t.getId(), id));
        if (b) save(load);
        return b;
    }

    /**
     * Finds a Telegram deliverer by its ID.
     *
     * @param id The ID of the Telegram deliverer to find.
     * @return The Telegram deliverer with the specified ID, or null if not found.
     */
    @Override
    public TelegramDeliverer findById(@NonNull UUID id) {
        return load().stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all Telegram deliverers from the repository.
     *
     * @return A list of all Telegram deliverers.
     */
    @Override
    public List<TelegramDeliverer> getAll() {
        return load();
    }

    /**
     * Loads Telegram deliverers from storage.
     *
     * @return A list of loaded Telegram deliverers.
     * @throws Exception If there's an error during deserialization.
     */
    @SneakyThrows
    @Override
    public List<TelegramDeliverer> load() {
        return jsonSerializer.read(TelegramDeliverer.class);
    }

    /**
     * Saves Telegram deliverers to storage.
     *
     * @param telegramDeliverers The list of Telegram deliverers to save.
     * @throws Exception If there's an error during serialization.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<TelegramDeliverer> telegramDeliverers) {
        jsonSerializer.write(telegramDeliverers);
    }
}
