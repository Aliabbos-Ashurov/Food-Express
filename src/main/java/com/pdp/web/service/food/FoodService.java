package com.pdp.web.service.food;

import com.pdp.web.model.food.Food;
import com.pdp.web.repository.food.FoodRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * Service interface for managing food items.
 * <p>
 * This interface defines methods for adding, removing, updating, searching,
 * and retrieving food items within the system.
 * </p>
 * <p>
 * Implementing classes should provide functionality to interact with a repository
 * storing food items, typically through a {@link FoodRepository}.
 * </p>
 *
 * @see FoodRepository For managing the storage of food items.
 * @see BaseService Base service interface providing common methods for service implementations.
 */
public interface FoodService extends BaseService<Food, List<Food>> {
    FoodRepository repository = new FoodRepository();
}
