package com.pdp.backend.web.service.banned;

import com.pdp.backend.web.model.banned.Banned;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the BannedService, providing logic to manage banned users.
 * Implements singleton pattern to ensure only one instance of the service is used.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BannedServiceImp implements BannedService {
    private static volatile BannedServiceImp instance;

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
    public boolean unbanUser(UUID userId) {
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

    @Override
    public boolean isUserBanned(UUID userId) {
        return getAll().stream().anyMatch(banned -> banned.getUserID().equals(userId));
    }

    @Override
    public boolean add(Banned banned) {
        return repository.add(banned);
    }

    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    @Override
    public boolean update(Banned object) {
        return false;
    }

    @Override
    public List<Banned> search(String query) {
        return null;
    }

    @Override
    public Banned getByID(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Banned> getAll() {
        return repository.getAll();
    }
}
