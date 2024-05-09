package com.pdp.backend.web.service.deliverer;

import com.pdp.backend.utils.Validator;
import com.pdp.backend.web.model.deliverer.Deliverer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Singleton service implementation to manage operations for Deliverer entities.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DelivererServiceImp implements DelivererService {
    private static volatile DelivererServiceImp instance;

    public static DelivererServiceImp getInstance() {
        if (instance == null) {
            synchronized (DelivererServiceImp.class){
                if (instance == null) {
                    instance = new DelivererServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new deliverer to the repository.
     * @param deliverer the {@link Deliverer} to be added
     * @return true if the deliverer was successfully added, false otherwise
     */
    @Override
    public boolean add(Deliverer deliverer) {
        return repository.add(deliverer);
    }

    /**
     * Removes a deliverer from the repository based on their ID.
     * @param id the UUID of the deliverer to be removed
     * @return true if the deliverer was successfully removed, false otherwise
     */
    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing deliverer's information in the repository. Currently not implemented.
     * @param deliverer the {@link Deliverer} to update
     * @return false, as the method is not currently implemented
     */
    @Override
    public boolean update(Deliverer deliverer) {
        return false;
    }

    /**
     * Searches for deliverers whose display names match the specified query string.
     * @param query the search query string
     * @return a list of {@link Deliverer} that match the search criteria
     */
    @Override
    public List<Deliverer> search(String query) {
        return getAll().stream()
                .filter(c -> Validator.isValid(c.getDisplayName(), query))
                .toList();
    }

    /**
     * Retrieves a deliverer by their UUID.
     * @param id the UUID of the deliverer to retrieve
     * @return the found {@link Deliverer}, or null if no deliverer is found
     */
    @Override
    public Deliverer getByID(UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all deliverers from the repository.
     * @return a list of all {@link Deliverer}
     */
    @Override
    public List<Deliverer> getAll() {
        return repository.getAll();
    }
}
