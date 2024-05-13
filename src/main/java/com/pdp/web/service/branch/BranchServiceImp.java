package com.pdp.web.service.branch;

import com.pdp.utils.Validator;
import com.pdp.web.model.branch.Branch;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Singleton service class for managing branch entities.
 * Provides methods to retrieve branches by brand, add, remove, update, and search for branches.
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
     * Retrieves a list of branches associated with a specific brand ID.
     *
     * @param brandID The UUID of the brand.
     * @return A list of Branch entities associated with the specified brand.
     */
    @Override
    public List<Branch> getBrandBranches(@NonNull UUID brandID) {
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
    public boolean add(@NonNull Branch branch) {
        return repository.add(branch);
    }

    /**
     * Removes a branch from the repository by its UUID.
     *
     * @param id The UUID of the branch to remove.
     * @return True if the branch was successfully removed, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
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
    public boolean update(@NonNull Branch branch) {
        return false;
    }

    /**
     * Searches for branches based on a location query.
     * Note: This method is currently a placeholder and returns null.
     *
     * @param query The search query for the branch location.
     * @return A list of branches that match the query (currently null).
     */
    @Override
    public List<Branch> search(@NonNull String query) {
        return null;
    }

    /**
     * Retrieves a branch from the repository by its UUID.
     *
     * @param id The UUID of the branch to retrieve.
     * @return The Branch object if found, or null if not found.
     */
    @Override
    public Branch getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all branches from the repository.
     *
     * @return A list of all Branch objects in the repository.
     */
    @Override
    public List<Branch> getAll() {
        return repository.getAll();
    }
}
