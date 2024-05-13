package com.pdp.web.service.discount;

import com.pdp.web.model.discount.Discount;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Singleton service implementation for managing discounts.
 * <p>
 * This service provides functionality to manage discounts within the system.
 * It follows the Singleton pattern to ensure a single instance is used throughout the application.
 * </p>
 *
 * @author Nishonov Doniyor
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiscountServiceImp implements DiscountService {
    private static volatile DiscountServiceImp instance;

    /**
     * Returns the singleton instance of DiscountServiceImp.
     *
     * @return the singleton instance
     */
    public static DiscountServiceImp getInstance() {
        if (instance == null) {
            synchronized (DiscountServiceImp.class) {
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
    public boolean add(@NonNull Discount discount) {
        return repository.add(discount);
    }

    /**
     * Removes a discount from the repository using its UUID.
     *
     * @param id the UUID of the discount to remove
     * @return true if the discount was successfully removed, false otherwise
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing discount. Currently not implemented.
     *
     * @param discount the {@link Discount} object to update
     * @return false, as the method is not implemented
     */
    @Override
    public boolean update(@NonNull Discount discount) {
        List<Discount> discounts = getAll();
        Optional<Discount> first = discounts.stream().filter(d -> Objects.equals(d.getId(), discount.getId())).findFirst();
        if (first.isPresent()) {
            updateDiscountData(first.get(), discount);
            repository.save(discounts);
            return true;
        }
        return false;
    }

    private void updateDiscountData(Discount currentDiscount, Discount updated) {
        currentDiscount.setDiscountPersentage(updated.isDiscountPersentage());
        currentDiscount.setFoodID(updated.getFoodID());
        currentDiscount.setDescriptionID(updated.getDescriptionID());
    }

    /**
     * Searches for discounts matching a specified query string. Currently not implemented.
     *
     * @param query the search query string
     * @return an empty list, as the method is not implemented
     */
    @Override
    public List<Discount> search(@NonNull String query) {
        return List.of();
    }

    /**
     * Retrieves a discount by its UUID.
     *
     * @param id the UUID of the discount to retrieve
     * @return the found {@link Discount}, or null if no discount is found
     */
    @Override
    public Discount getByID(@NonNull UUID id) {
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
