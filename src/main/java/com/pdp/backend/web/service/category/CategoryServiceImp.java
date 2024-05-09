package com.pdp.backend.web.service.category;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryServiceImp {
    private static CategoryServiceImp instance;

    public static CategoryServiceImp getInstance() {
        if (instance == null) {
            instance = new CategoryServiceImp();
        }
        return instance;
    }
}
