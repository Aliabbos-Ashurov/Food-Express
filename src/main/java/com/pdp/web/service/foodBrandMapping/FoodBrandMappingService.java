package com.pdp.web.service.foodBrandMapping;

import com.pdp.web.model.foodBrandMapping.FoodBrandMapping;
import com.pdp.web.repository.foodBrandMapping.FoodBrandMappingRepository;
import com.pdp.web.service.BaseService;

import java.util.List;
import java.util.UUID;


/**
 * Service interface for managing the mapping between food items and brands.
 * This includes operations such as retrieving food items for a specific brand and category.
 *
 * @see BaseService
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
    List<FoodBrandMapping> getFoodsByBrand(UUID brandId);

    /**
     * Retrieves a list of food items associated with a particular brand filtered by category name.
     *
     * @param categoryName The name of the category by which to filter food items.
     * @return A list of {@link FoodBrandMapping} representing the food items within the specified category.
     */
    List<FoodBrandMapping> getBrandFoodsByCategoryName(String categoryName);
    boolean isFoodFromBrand(UUID foodID, UUID brandID);
}
