package com.pdp.backend.web.service.branch;

import com.pdp.backend.web.model.branch.Branch;
import com.pdp.backend.web.repository.branch.BranchRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;
import java.util.UUID;

/**
 * Defines the business logic for branch-related operations.
 * This interface is meant to manage the data access for the {@link Branch} model.
 * It extends the generic BaseService interface, inheriting common CRUD operations.
 *
 * @see BaseService
 * @see Branch
 */
public interface BranchService extends BaseService<Branch, List<Branch>> {
    BranchRepository repository = new BranchRepository();

    /**
     * Retrieves a list of branches associated with a particular brand, identified by UUID.
     *
     * @param brandID The unique identifier of the brand.
     * @return A list of {@link Branch} instances associated with the specified brand.
     */
    List<Branch> getBrandBranches(UUID brandID);
}
