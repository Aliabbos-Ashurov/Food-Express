package com.pdp.web.service.banned;

import com.pdp.web.model.banned.Banned;
import com.pdp.web.repository.banned.BannedRepository;
import com.pdp.web.service.BaseService;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing bans on users.
 * This interface extends the generic BaseService interface for common CRUD operations
 * and provides additional methods specific to the Banned entity.
 * <p>
 * This service interacts with the {@link BannedRepository} for persistence operations.
 * </p>
 *
 * @see BaseService
 * @see Banned
 * @see BannedRepository
 */
public interface BannedService extends BaseService<Banned, List<Banned>> {
    BannedRepository repository = new BannedRepository();

    /**
     * Unbans a user, allowing them to access  again.
     *
     * @param userId The unique identifier of the user to unban.
     * @return {@code true} if the user is successfully unbanned; {@code false} otherwise.
     */
    boolean unbanUser(@NonNull UUID userId);

    /**
     * Checks whether a user is currently banned.
     *
     * @param userId The unique identifier of the user.
     * @return {@code true} if the user is banned; {@code false} otherwise.
     */
    boolean isUserBanned(@NonNull UUID userId);
}

