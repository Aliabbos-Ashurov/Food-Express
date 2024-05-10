package com.pdp.backend.web.service.branch;

import com.pdp.backend.utils.Validator;
import com.pdp.backend.web.model.branch.Branch;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Singleton service class for managing branch entities.
 * Provides methods to get branches by brand, add, remove, and search branches.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchServiceImp implements BranchService {
    private static volatile BranchServiceImp instance;

    public static BranchServiceImp getInstance() {
        if (instance == null) {
            synchronized (BranchServiceImp.class) {
                if (instance == null) {
                    instance = new BranchServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Gets a list of branches associated with a specific brand ID.
     *
     * @param brandID The UUID of the brand.
     * @return A list of Branch entities.
     */
    @Override
    public List<Branch> getBrandBranches(UUID brandID) {
        List<Branch> branches = getAll();
        return branches.stream()
                .filter(branch -> branch.getBrandID().equals(brandID))
                .toList();
    }

    /**
     * Adds a new branch to the repository.
     *
     * @param branch The Branch object to add.
     * @return True if the branch was successfully added, false otherwise.
     */
    @Override
    public boolean add(Branch branch) {
        return repository.add(branch);
    }

    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing branch.
     * Currently, this method is a placeholder and is not implemented.
     *
     * @param branch The Branch object with updated information.
     * @return False as the method is not implemented.
     */
    @Override
    public boolean update(Branch branch) {
        return false;
    }

    /**
     * Searches for branches based on a location query.
     *
     * @param query The search query for the branch location.
     * @return A list of branches that match the query.
     */
    @Override
    public List<Branch> search(String query) {
        List<Branch> branches = getAll();
        return branches.stream()
                .filter(branch -> Validator.isValid(branch.getLocation(), query))
                .toList();
    }

    @Override
    public Branch getByID(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Branch> getAll() {
        return repository.getAll();
    }
}
