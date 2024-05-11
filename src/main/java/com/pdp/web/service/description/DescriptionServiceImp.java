package com.pdp.web.service.description;

import com.pdp.utils.Validator;
import com.pdp.web.model.description.Description;
import com.pdp.web.service.discount.DiscountServiceImp;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Singleton service implementation for managing descriptions.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DescriptionServiceImp implements DescriptionService{
    private static volatile DescriptionServiceImp instance;

    public static DescriptionServiceImp getInstance() {
        if (instance == null) {
            synchronized (DiscountServiceImp.class){
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
    public boolean add(Description description) {
        return repository.add(description);
    }

    /**
     * Removes a description from the repository using its UUID.
     *
     * @param id the UUID of the description to remove
     * @return true if the description was successfully removed, false otherwise
     */
    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing description. Currently not implemented.
     *
     * @param description the {@link Description} to update
     * @return false, as the method is not implemented
     */
    @Override
    public boolean update(Description description) {
        for (int i = 0; i < getAll().size(); i++) {
            if(description.getDisplayName().equals(getAll().get(i).getDisplayName())){
                getAll().set(i,description);
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for descriptions matching a specified query string.
     *
     * @param query the query string used for searching
     * @return a list of {@link Description} objects that match the query
     */    @Override
    public List<Description> search(String query) {
        return getAll().stream()
                .filter(d-> Validator.isValid(d.getDisplayName(),query))
                .toList();
    }

    /**
     * Retrieves a description by its UUID.
     *
     * @param id the UUID of the description to retrieve
     * @return the found {@link Description}, or null if no description is found
     */
    @Override
    public Description getByID(UUID id) {
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
