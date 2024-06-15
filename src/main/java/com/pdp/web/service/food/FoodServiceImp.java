package com.pdp.web.service.food;

import com.pdp.utils.Validator;
import com.pdp.web.model.food.Food;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * A singleton service class for managing food items.
 * Implements thread-safe lazy initialization with double-checked locking.
 *
 * @author Nishonov Doniyor
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FoodServiceImp implements FoodService {
    private static volatile FoodServiceImp instance;

    public static FoodServiceImp getInstance() {
        if (instance == null) {
            synchronized (FoodServiceImp.class) {
                if (instance == null) {
                    instance = new FoodServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new food item to the repository.
     *
     * @param food the {@link Food} object to be added
     * @return true if the food item was successfully added, false otherwise
     */
    @Override
    public boolean add(@NonNull Food food) {
        return repository.add(food);
    }

    /**
     * Removes a food item from the repository using its UUID.
     *
     * @param id the UUID of the food item to be removed
     * @return true if the food item was successfully removed, false otherwise
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing food item in the repository. Currently not implemented.
     *
     * @param food the {@link Food} object to update
     * @return false, indicating the operation is not supported yet
     */
    @Override
    public boolean update(@NonNull Food food) {
        return repository.update(food);
    }

    /**
     * Searches for food items that match a given query string.
     *
     * @param query the query string to match against food item display names
     * @return a list of {@link Food} objects that match the query
     */
    @Override
    public List<Food> search(@NonNull String query) {
        return getAll().stream()
                .filter(f -> Validator.isValid(f.getDisplayName(), query))
                .toList();
    }

    /**
     * Retrieves a food item by its UUID.
     *
     * @param id the UUID of the food item to retrieve
     * @return the {@link Food} object, or null if no food item is found
     */
    @Override
    public Food getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all food items available in the repository.
     *
     * @return a list of all {@link Food} objects
     */
    @Override
    public List<Food> getAll() {
        return repository.getAll();
    }
}
