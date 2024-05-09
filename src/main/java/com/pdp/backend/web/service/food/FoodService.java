package com.pdp.backend.web.service.food;

import com.pdp.backend.web.model.food.Food;
import com.pdp.backend.web.repository.food.FoodRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

public interface FoodService extends BaseService<Food, List<Food>> {
    FoodRepository repository = new FoodRepository();
}
