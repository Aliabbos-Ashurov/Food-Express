package com.pdp.web.service.foodBrandMapping;

import com.pdp.web.model.foodBrandMapping.FoodBrandMapping;
import com.pdp.web.repository.foodBrandMapping.FoodBrandMappingRepository;
import com.pdp.web.service.BaseService;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;


/**
 * Service interface for managing the mapping between food items and brands.
 * This includes operations such as retrieving food items for a specific brand and category.
 * @author Nishonov Doniyor
 * @param <T> the type of food brand mapping entity
 * @param <L> the type of list containing food brand mappings
 * @see BaseService Base service interface providing common methods for service implementations.
 * @see FoodBrandMapping
 */
public interface FoodBrandMappingService extends BaseService<FoodBrandMapping, List<FoodBrandMapping>> {
    FoodBrandMappingRepository repository = FoodBrandMappingRepository.getInstance();

    /**
     * Retrieves a list of food items associated with a particular brand by its UUID.
     *
     * @param brandId The UUID of the brand for which food items are to be retrieved.
     * @return A list of {@link FoodBrandMapping} representing the food items associated with the brand.
     */
    List<FoodBrandMapping> getFoodsByBrand(@NonNull UUID brandId);

    /**
     * Retrieves a list of food items associated with a particular brand filtered by category name.
     *
     * @param brandID      The UUID of the brand.
     * @param categoryName The name of the category by which to filter food items.
     * @return A list of {@link FoodBrandMapping} representing the food items within the specified category.
     */
    List<FoodBrandMapping> getBrandFoodsByCategoryName(@NonNull UUID brandID, String categoryName);

    /**
     * Checks if a food item belongs to a specific brand.
     *
     * @param foodID  The UUID of the food item.
     * @param brandID The UUID of the brand.
     * @return true if the food item belongs to the brand, false otherwise.
     */
    boolean isFoodFromBrand(@NonNull UUID foodID, @NonNull UUID brandID);
}
