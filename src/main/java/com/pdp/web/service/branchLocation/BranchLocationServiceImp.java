package com.pdp.web.service.branchLocation;

import com.pdp.dto.LocationDTO;
import com.pdp.web.model.branchLocation.BranchLocation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link BranchLocationService} for managing branch location entities.
 * This service provides operations to interact with {@link BranchLocation} objects.
 *
 * This class follows the singleton pattern to ensure a single instance is used throughout the application.
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
     *         or {@code null} if no location is found for the given branch ID.
     */
    @Override
    public LocationDTO getBranchLocation(UUID branchID) {
        List<BranchLocation> branchLocations = repository.getAll();
        BranchLocation location = branchLocations.stream()
                .filter(branchLocation -> branchLocation.getBranchID().equals(branchID))
                .findFirst()
                .orElse(null);
        return Objects.nonNull(location) ? new LocationDTO(location.getLatidue(), location.getLongtidue()) : null;
    }
    @Override
    public boolean add(BranchLocation branchLocation) {
        return repository.add(branchLocation);
    }

    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    @Override
    public boolean update(BranchLocation object) {
        return false;
    }

    @Override
    public List<BranchLocation> search(String query) {
        return null;
    }

    @Override
    public BranchLocation getByID(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<BranchLocation> getAll() {
        return repository.getAll();
    }
}
