package com.pdp.backend.web.service.foodBrandMapping;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FoodBrandMappingServiceImp {
    private static FoodBrandMappingServiceImp instance;

    public static FoodBrandMappingServiceImp getInstance() {
        if (instance == null) {
            instance = new FoodBrandMappingServiceImp();
        }
        return instance;
    }
}
