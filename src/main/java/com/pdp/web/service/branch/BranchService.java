package com.pdp.web.service.branch;

import com.pdp.web.model.branch.Branch;
import com.pdp.web.repository.branch.BranchRepository;
import com.pdp.web.service.BaseService;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Interface defining the business logic for branch-related operations.
 * This interface manages the data access for the {@link Branch} model and extends the generic BaseService interface.
 * It inherits common CRUD operations for managing Branch entities.
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
    List<Branch> getBrandBranches(@NonNull UUID brandID);
}
