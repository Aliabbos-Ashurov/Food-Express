package com.pdp.web.service.banned;

import com.pdp.web.model.banned.Banned;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the BannedService, providing logic to manage banned users.
 * Implements singleton pattern to ensure only one instance of the service is used.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BannedServiceImp implements BannedService {
    private static volatile BannedServiceImp instance;

    /**
     * Retrieves the singleton instance of BannedServiceImp.
     *
     * @return The singleton instance of BannedServiceImp.
     */
    public static BannedServiceImp getInstance() {
        if (instance == null)
            synchronized (BannedServiceImp.class) {
                if (instance == null) {
                    instance = new BannedServiceImp();
                }
            }
        return instance;
    }

    /**
     * Unbans a user by their UUID, if they are currently banned.
     *
     * @param userId UUID of the user to unban.
     * @return true if the user was successfully unbanned; false otherwise.
     */
    @Override
    public boolean unbanUser(@NonNull UUID userId) {
        List<Banned> bannedList = getAll();
        return getAll().stream()
                .filter(banned -> banned.getUserID().equals(userId))
                .findFirst()
                .map(banned -> {
                    repository.remove(banned.getId());
                    return true;
                })
                .orElse(false);
    }

    /**
     * Checks whether a user identified by their UUID is currently banned.
     *
     * @param userId UUID of the user to check.
     * @return true if the user is banned; false otherwise.
     */
    @Override
    public boolean isUserBanned(@NonNull UUID userId) {
        return getAll().stream().anyMatch(banned -> banned.getUserID().equals(userId));
    }

    /**
     * Adds a new banned user entry to the repository.
     *
     * @param banned The Banned object representing the user to be banned.
     * @return true if the user was successfully banned; false otherwise.
     */
    @Override
    public boolean add(@NonNull Banned banned) {
        return repository.add(banned);
    }

    /**
     * Removes a banned user entry from the repository by its UUID.
     *
     * @param id UUID of the banned user entry to remove.
     * @return true if the user was successfully removed from the banned list; false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates a banned user entry in the repository.
     *
     * @param banned The updated Banned object.
     * @return true if the update was successful; false otherwise (typically not supported for banned users).
     */
    @Override
    public boolean update(@NonNull Banned banned) {
        return repository.update(banned);
    }

    /**
     * Searches for banned users based on a specified query (not typically applicable for banned users).
     *
     * @param query The search query (not applicable for banned users).
     * @return null (not typically applicable for banned users).
     */
    @Override
    public List<Banned> search(@NonNull String query) {
        return null;
    }

    @Override
    public Banned getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Banned> getAll() {
        return repository.getAll();
    }
}
