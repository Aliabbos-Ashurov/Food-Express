package com.pdp.telegram.repository.telegramTransport;

import com.pdp.json.serializer.JsonSerializer;
import com.pdp.telegram.model.telegramTransport.TelegramTransport;
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
 * Repository class for managing Telegram transports.
 * Handles data storage and retrieval.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 14:42
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramTransportRepository implements BaseRepository<TelegramTransport, List<TelegramTransport>> {
    private static volatile TelegramTransportRepository instance;
    private static JsonSerializer<TelegramTransport> jsonSerializer;

    /**
     * Returns the singleton instance of the repository.
     *
     * @return The singleton instance of the repository.
     */
    public static TelegramTransportRepository getInstance() {
        if (instance == null) {
            synchronized (TelegramTransportRepository.class) {
                if (instance == null) {
                    instance = new TelegramTransportRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_TELEGRAM_TRANSPORT));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a Telegram transport to the repository.
     *
     * @param object The Telegram transport to add.
     * @return True if the addition was successful, false otherwise.
     */
    @Override
    public boolean add(@NonNull TelegramTransport object) {
        List<TelegramTransport> load = load();
        load.add(object);
        save(load);
        return true;
    }

    /**
     * Removes a Telegram transport from the repository.
     *
     * @param id The ID of the Telegram transport to remove.
     * @return True if the removal was successful, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<TelegramTransport> load = load();
        boolean b = load.removeIf(t -> Objects.equals(t.getId(), id));
        if (b) save(load);
        return b;
    }

    /**
     * Finds a Telegram transport by its ID.
     *
     * @param id The ID of the Telegram transport to find.
     * @return The Telegram transport with the specified ID, or null if not found.
     */
    @Override
    public TelegramTransport findById(@NonNull UUID id) {
        return load().stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all Telegram transports from the repository.
     *
     * @return A list of all Telegram transports.
     */
    @Override
    public List<TelegramTransport> getAll() {
        return load();
    }

    /**
     * Loads Telegram transports from storage.
     *
     * @return A list of loaded Telegram transports.
     * @throws Exception If there's an error during deserialization.
     */
    @SneakyThrows
    @Override
    public List<TelegramTransport> load() {
        return jsonSerializer.read(TelegramTransport.class);
    }

    /**
     * Saves Telegram transports to storage.
     *
     * @param telegramTransports The list of Telegram transports to save.
     * @throws Exception If there's an error during serialization.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<TelegramTransport> telegramTransports) {
        jsonSerializer.write(telegramTransports);
    }
}
