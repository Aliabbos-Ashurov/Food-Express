package com.pdp.telegram.service.telegramTransport;

import com.pdp.telegram.model.telegramTransport.TelegramTransport;
import com.pdp.utils.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Implementation class for managing Telegram transports.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 15:15
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramTransportServiceImp implements TelegramTransportService {
    private static volatile TelegramTransportServiceImp instance;
    /**
     * Returns the singleton instance of the service.
     *
     * @return The singleton instance of the service.
     */
    public static TelegramTransportServiceImp getInstance() {
        if (instance == null) {
            synchronized (TelegramTransportServiceImp.class) {
                if (instance == null) {
                    instance = new TelegramTransportServiceImp();
                }
            }
        }
        return instance;
    }
    /**
     * Adds a Telegram transport.
     *
     * @param telegramTransport The Telegram transport to add.
     * @return True if the addition was successful, false otherwise.
     */
    @Override
    public boolean add(@NonNull TelegramTransport telegramTransport) {
        return repository.add(telegramTransport);
    }
    /**
     * Removes a Telegram transport by its ID.
     *
     * @param id The ID of the Telegram transport to remove.
     * @return True if the removal was successful, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }
    /**
     * Updates a Telegram transport.
     *
     * @param object The Telegram transport to update.
     * @return Always returns false as update functionality is not implemented.
     */
    @Override
    public boolean update(@NonNull TelegramTransport object) {
        return false;
    }
    /**
     * Searches for Telegram transports based on a query string.
     *
     * @param query The search query.
     * @return A list of Telegram transports matching the query.
     */
    @Override
    public List<TelegramTransport> search(@NonNull String query) {
        return getAll().stream()
                .filter(t -> Validator.isValid(t.getDisplayName(), query))
                .toList();
    }
    /**
     * Retrieves a Telegram transport by its ID.
     *
     * @param id The ID of the Telegram transport to retrieve.
     * @return The Telegram transport with the specified ID.
     */
    @Override
    public TelegramTransport getByID(@NonNull UUID id) {
        return repository.findById(id);
    }
    /**
     * Retrieves all Telegram transports.
     *
     * @return A list of all Telegram transports.
     */
    @Override
    public List<TelegramTransport> getAll() {
        return repository.getAll();
    }
}
