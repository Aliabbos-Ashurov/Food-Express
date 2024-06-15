package com.pdp.web.service.branchLocation;

import com.pdp.dto.LocationDTO;
import com.pdp.web.model.branchLocation.BranchLocation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link BranchLocationService} for managing branch location entities.
 * This service provides operations to interact with {@link BranchLocation} objects.
 * <p>
 * This class follows the singleton pattern to ensure a single instance is used throughout the application.
 * </p>
 *
 * @author Aliabbos Ashurov
 * @since 11/May/2024 10:25
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchLocationServiceImp implements BranchLocationService {
    private static volatile BranchLocationServiceImp instance;

    /**
     * Returns a singleton instance of {@link BranchLocationServiceImp}.
     *
     * @return The singleton instance of {@link BranchLocationServiceImp}.
     */
    public static BranchLocationServiceImp getInstance() {
        if (instance == null) {
            synchronized (BranchLocationServiceImp.class) {
                if (instance == null) {
                    instance = new BranchLocationServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Retrieves the location DTO for the specified branch ID.
     *
     * @param branchID The UUID of the branch to retrieve the location for.
     * @return The {@link LocationDTO} representing the location of the branch,
     * or {@code null} if no location is found for the given branch ID.
     */
    @Override
    public LocationDTO getBranchLocation(@NonNull UUID branchID) {
        List<BranchLocation> branchLocations = repository.getAll();
        BranchLocation location = branchLocations.stream()
                .filter(branchLocation -> branchLocation.getBranchID().equals(branchID))
                .findFirst()
                .orElse(null);
        return Objects.nonNull(location) ? new LocationDTO(location.getLatidue(), location.getLongtidue()) : null;
    }

    /**
     * Adds a new branch location to the repository.
     *
     * @param branchLocation The BranchLocation object representing the branch location to add.
     * @return {@code true} if the branch location was successfully added; {@code false} otherwise.
     */
    @Override
    public boolean add(@NonNull BranchLocation branchLocation) {
        return repository.add(branchLocation);
    }

    /**
     * Removes a branch location from the repository by its UUID.
     *
     * @param id The UUID of the branch location to remove.
     * @return {@code true} if the branch location was successfully removed; {@code false} otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing branch location in the repository.
     *
     * @param object The BranchLocation object with updated information.
     * @return {@code false} as updating branch locations may not be supported.
     */
    @Override
    public boolean update(@NonNull BranchLocation branchLocation) {
        return repository.update(branchLocation);
    }

    /**
     * Searches for branch locations based on a query (not applicable for branch locations).
     *
     * @param query The search query for branch locations (not applicable).
     * @return {@code null} as searching for branch locations by query is not supported.
     */
    @Override
    public List<BranchLocation> search(@NonNull String query) {
        return null;
    }

    /**
     * Retrieves a branch location from the repository by its UUID.
     *
     * @param id The UUID of the branch location to retrieve.
     * @return The BranchLocation object representing the branch location, or {@code null} if not found.
     */
    @Override
    public BranchLocation getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all branch locations from the repository.
     *
     * @return A list of all BranchLocation objects in the repository.
     */
    @Override
    public List<BranchLocation> getAll() {
        return repository.getAll();
    }
}
