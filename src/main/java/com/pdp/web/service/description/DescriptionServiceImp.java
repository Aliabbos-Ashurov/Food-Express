package com.pdp.web.service.description;

import com.pdp.utils.Validator;
import com.pdp.web.model.description.Description;
import com.pdp.web.service.discount.DiscountServiceImp;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Singleton service implementation for managing descriptions.
 * <p>
 * This service provides functionality to manage descriptions within the system.
 * It follows the Singleton pattern to ensure a single instance is used throughout the application.
 * </p>
 *
 * @author Nishonov Doniyor
 * @see DiscountServiceImp For managing discounts, as it may relate to description operations.
 * @since 13/May/2024  21:09
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DescriptionServiceImp implements DescriptionService {
    private static volatile DescriptionServiceImp instance;

    /**
     * Returns the singleton instance of DescriptionServiceImp.
     *
     * @return the singleton instance
     * @see DiscountServiceImp#getInstance() For obtaining the singleton instance of DiscountService.
     */
    public static DescriptionServiceImp getInstance() {
        if (instance == null) {
            synchronized (DiscountServiceImp.class) {
                if (instance == null) {
                    instance = new DescriptionServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new description to the repository.
     *
     * @param description the {@link Description} to be added
     * @return true if the description was successfully added, false otherwise
     */
    @Override
    public boolean add(@NonNull Description description) {
        return repository.add(description);
    }

    /**
     * Removes a description from the repository using its UUID.
     *
     * @param id the UUID of the description to remove
     * @return true if the description was successfully removed, false otherwise
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing description. Currently not implemented.
     *
     * @param description the {@link Description} to update
     * @return false, as the method is not implemented
     */
    @Override
    public boolean update(@NonNull Description description) {
        return repository.update(description);
    }

    /**
     * Searches for descriptions matching a specified query string.
     *
     * @param query the query string used for searching
     * @return a list of {@link Description} objects that match the query
     */
    @Override
    public List<Description> search(@NonNull String query) {
        return getAll().stream()
                .filter(d -> Validator.isValid(d.getDisplayName(), query))
                .toList();
    }

    /**
     * Retrieves a description by its UUID.
     *
     * @param id the UUID of the description to retrieve
     * @return the found {@link Description}, or null if no description is found
     */
    @Override
    public Description getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all descriptions stored in the repository.
     *
     * @return a list of all {@link Description} objects
     */
    @Override
    public List<Description> getAll() {
        return repository.getAll();
    }
}
