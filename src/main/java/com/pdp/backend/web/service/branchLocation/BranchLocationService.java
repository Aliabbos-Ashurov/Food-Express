package com.pdp.backend.web.service.branchLocation;

import com.pdp.backend.dto.LocationDTO;
import com.pdp.backend.web.model.branchLocation.BranchLocation;
import com.pdp.backend.web.repository.branchLocation.BranchLocationRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing branch locations.
 * Extends {@link BaseService} to provide CRUD operations for branch locations.
 * <p>
 * This service interface defines methods to retrieve and manipulate branch location data.
 *
 * @author Aliabbos Ashurov
 * @since 11/May/2024 10:23
 */
public interface BranchLocationService extends BaseService<BranchLocation, List<BranchLocation>> {
    BranchLocationRepository repository = BranchLocationRepository.getInstance();

    /**
     * Retrieves the location DTO for the specified branch ID.
     *
     * @param branchID The UUID of the branch to retrieve the location for.
     * @return The {@link LocationDTO} representing the location of the branch.
     */
    LocationDTO getBranchLocation(UUID branchID);
}
