package com.pdp.web.service.foodBrandMapping;

import com.pdp.web.model.foodBrandMapping.FoodBrandMapping;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Singleton service class for managing food brand mappings.
 * Implements thread-safe lazy initialization with double-checked locking.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FoodBrandMappingServiceImp implements FoodBrandMappingService {
    private static volatile FoodBrandMappingServiceImp instance;

    public static FoodBrandMappingServiceImp getInstance() {
        if (instance == null) {
            synchronized (FoodBrandMappingServiceImp.class) {
                if (instance == null) {
                    instance = new FoodBrandMappingServiceImp();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean isFoodFromBrand(UUID foodID, UUID brandID) {
        List<FoodBrandMapping> foodBrandMappings = getAll();
        return foodBrandMappings.stream()
                .anyMatch(foodBrandMapping -> foodBrandMapping.getBrandID().equals(brandID) && foodBrandMapping.getFoodID().equals(foodID));
    }

    /**
     * Retrieves all food brand mappings associated with a specific brand ID.
     *
     * @param brandId the UUID of the brand to filter mappings
     * @return a list of {@link FoodBrandMapping} associated with the specified brand
     */
    @Override
    public List<FoodBrandMapping> getFoodsByBrand(UUID brandId) {
        return getAll().stream()
                .filter(f -> Objects.equals(f.getBrandID(), brandId))
                .toList();
    }

    /**
     * Retrieves all food brand mappings associated with a specific category name.
     *
     * @param categoryName the name of the category to filter mappings
     * @return a list of {@link FoodBrandMapping} within the specified category
     */
    @Override
    public List<FoodBrandMapping> getBrandFoodsByCategoryName(UUID brandID, String categoryName) {
        return getAll().stream()
                .filter(o -> Objects.equals(o.getCategoryName(),categoryName) && Objects.equals(o.getBrandID(),brandID))
                .toList();
    }

    /**
     * Adds a new food brand mapping to the repository.
     *
     * @param foodBrandMapping the {@link FoodBrandMapping} to add
     * @return true if the mapping was successfully added, false otherwise
     */
    @Override
    public boolean add(FoodBrandMapping foodBrandMapping) {
        return repository.add(foodBrandMapping);
    }

    /**
     * Removes a food brand mapping from the repository using its UUID.
     *
     * @param id the UUID of the mapping to be removed
     * @return true if the mapping was successfully removed, false otherwise
     */
    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing food brand mapping in the repository. Currently not implemented.
     *
     * @param foodBrandMapping the {@link FoodBrandMapping} to update
     * @return false, indicating the operation is not supported yet
     */
    @Override
    public boolean update(FoodBrandMapping foodBrandMapping) {
        return false;
    }

    /**
     * Searches for food brand mappings based on a query string. Currently not implemented.
     *
     * @param query the search query string
     * @return an empty list, as the method is not implemented
     */
    @Override
    public List<FoodBrandMapping> search(String query) {
        return List.of();
    }

    /**
     * Retrieves a food brand mapping by its UUID.
     *
     * @param id the UUID of the mapping to retrieve
     * @return the {@link FoodBrandMapping}, or null if no mapping is found
     */
    @Override
    public FoodBrandMapping getByID(UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all food brand mappings from the repository.
     *
     * @return a list of all {@link FoodBrandMapping}
     */
    @Override
    public List<FoodBrandMapping> getAll() {
        return repository.getAll();
    }
}
