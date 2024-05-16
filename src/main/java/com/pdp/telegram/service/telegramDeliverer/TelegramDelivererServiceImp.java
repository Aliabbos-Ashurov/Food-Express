package com.pdp.telegram.service.telegramDeliverer;

import com.pdp.telegram.model.telegramDeliverer.TelegramDeliverer;
import com.pdp.utils.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation class for managing Telegram deliverers.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 15:13
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramDelivererServiceImp implements TelegramDelivererService {
    private static volatile TelegramDelivererServiceImp instance;

    /**
     * Returns the singleton instance of the service.
     *
     * @return The singleton instance of the service.
     */
    public static TelegramDelivererServiceImp getInstance() {
        if (instance == null) {
            synchronized (TelegramDelivererServiceImp.class) {
                if (instance == null) {
                    instance = new TelegramDelivererServiceImp();
                }
            }
        }
        return instance;
    }

    @Override
    public TelegramDeliverer getDeliverByTelegramId(@NonNull UUID telegramUserID) {
        List<TelegramDeliverer> telegramDeliverers = getAll();
        return telegramDeliverers.stream()
                .filter(telegramDeliverer -> telegramDeliverer.getTelegramUserID().equals(telegramUserID))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a Telegram deliverer if not already existing.
     *
     * @param telegramDeliverer The Telegram deliverer to add.
     * @return True if the addition was successful, false if the deliverer already exists.
     */
    @Override
    public boolean add(@NonNull TelegramDeliverer telegramDeliverer) {
        List<TelegramDeliverer> telegramDeliverers = getAll();
        boolean anyMatch = telegramDeliverers.stream().anyMatch(t -> Objects.equals(t.getTelegramUserID(), telegramDeliverer.getTelegramUserID()));
        if (!anyMatch) return false;
        repository.add(telegramDeliverer);
        return true;
    }

    /**
     * Removes a Telegram deliverer by its ID.
     *
     * @param id The ID of the Telegram deliverer to remove.
     * @return True if the removal was successful, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates a Telegram deliverer's data.
     *
     * @param telegramDeliverer The updated Telegram deliverer.
     * @return True if the update was successful, false if the deliverer was not found.
     */
    @Override
    public boolean update(@NonNull TelegramDeliverer telegramDeliverer) {
        List<TelegramDeliverer> telegramDeliverers = getAll();
        Optional<TelegramDeliverer> first = telegramDeliverers.stream()
                .filter(t -> Objects.equals(t.getId(), telegramDeliverer.getId()))
                .findFirst();
        if (first.isPresent()) {
            updateTelegramDelivererData(first.get(), telegramDeliverer);
            repository.save(telegramDeliverers);
            return true;
        }
        return false;
    }

    /**
     * Helper method to update the data of a Telegram deliverer.
     *
     * @param deliverer The Telegram deliverer to update.
     * @param updated   The updated data of the Telegram deliverer.
     */
    private void updateTelegramDelivererData(TelegramDeliverer deliverer, TelegramDeliverer updated) {
        deliverer.setFullname(updated.getFullname());
        deliverer.setTelegramUserID(updated.getTelegramUserID());
    }

    /**
     * Searches for Telegram deliverers based on a query string.
     *
     * @param query The search query.
     * @return A list of Telegram deliverers matching the query.
     */
    @Override
    public List<TelegramDeliverer> search(@NonNull String query) {
        return getAll().stream()
                .filter(t -> Validator.isValid(t.getDisplayName(), query))
                .toList();
    }

    /**
     * Retrieves a Telegram deliverer by its ID.
     *
     * @param id The ID of the Telegram deliverer to retrieve.
     * @return The Telegram deliverer with the specified ID.
     */
    @Override
    public TelegramDeliverer getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all Telegram deliverers.
     *
     * @return A list of all Telegram deliverers.
     */
    @Override
    public List<TelegramDeliverer> getAll() {
        return repository.getAll();
    }
}
