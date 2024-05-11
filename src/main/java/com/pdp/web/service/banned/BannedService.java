package com.pdp.web.service.banned;

import com.pdp.web.model.banned.Banned;
import com.pdp.web.repository.banned.BannedRepository;
import com.pdp.web.service.BaseService;

import java.util.List;
import java.util.UUID;

/**
 * Interface for services related to managing bans on users.
 * This service extends the generic BaseService interface for common CRUD operations.
 * It provides additional methods specific to the data type {@link Banned}.
 *
 * @see BaseService
 * @see Banned
 */
public interface BannedService extends BaseService<Banned, List<Banned>> {
    BannedRepository repository = BannedRepository.getInstance();

    /**
     * Unbans a user, allowing them access again.
     *
     * @param userId The unique identifier of the user to unban.
     * @return {@code true} if the user is successfully unbanned; {@code false} otherwise.
     */
    boolean unbanUser(UUID userId);

    /**
     * Checks whether a user is currently banned.
     *
     * @param userId The unique identifier of the user.
     * @return {@code true} if the user is banned; {@code false} otherwise.
     */
    boolean isUserBanned(UUID userId);
}

