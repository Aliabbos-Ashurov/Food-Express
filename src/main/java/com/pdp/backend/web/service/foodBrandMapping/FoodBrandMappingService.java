package com.pdp.backend.web.service.foodBrandMapping;

import com.pdp.backend.web.model.foodBrandMapping.FoodBrandMapping;
import com.pdp.backend.web.repository.foodBrandMapping.FoodBrandMappingRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

public interface FoodBrandMappingService extends BaseService<FoodBrandMapping, List<FoodBrandMapping>> {
    FoodBrandMappingRepository repository = new FoodBrandMappingRepository();
}
