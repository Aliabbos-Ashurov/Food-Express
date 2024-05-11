package com.pdp.web.service.food;

import com.pdp.web.model.food.Food;
import com.pdp.web.repository.food.FoodRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

public interface FoodService extends BaseService<Food, List<Food>> {
    FoodRepository repository = FoodRepository.getInstance();
}
