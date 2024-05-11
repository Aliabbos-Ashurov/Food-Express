package com.pdp.web.service.discount;

import com.pdp.web.model.discount.Discount;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Singleton service implementation for managing discounts.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiscountServiceImp implements DiscountService {
    private static volatile DiscountServiceImp instance;

    public static DiscountServiceImp getInstance() {
        if (instance == null) {
            synchronized (DiscountServiceImp.class){
                if (instance == null) {
                    instance = new DiscountServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new discount to the repository.
     *
     * @param discount the {@link Discount} object to add
     * @return true if the discount was successfully added, false otherwise
     */
    @Override
    public boolean add(Discount discount) {
        return repository.add(discount);
    }

    /**
     * Removes a discount from the repository using its UUID.
     *
     * @param id the UUID of the discount to remove
     * @return true if the discount was successfully removed, false otherwise
     */
    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing discount. Currently not implemented.
     *
     * @param discount the {@link Discount} object to update
     * @return false, as the method is not implemented
     */
    @Override
    public boolean update(Discount discount) {
        return false;
    }

    /**
     * Searches for discounts matching a specified query string. Currently not implemented.
     *
     * @param query the search query string
     * @return an empty list, as the method is not implemented
     */
    @Override
    public List<Discount> search(String query) {
        return List.of();
    }

    /**
     * Retrieves a discount by its UUID.
     *
     * @param id the UUID of the discount to retrieve
     * @return the found {@link Discount}, or null if no discount is found
     */
    @Override
    public Discount getByID(UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all discounts stored in the repository.
     *
     * @return a list of all {@link Discount} objects
     */
    @Override
    public List<Discount> getAll() {
        return repository.getAll();
    }
}
