package com.pdp.backend.web.service.food;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FoodServiceImp {
    private static FoodServiceImp instance;

    public static FoodServiceImp getInstance() {
        if (instance == null) {
            instance = new FoodServiceImp();
        }
        return instance;
    }
}
