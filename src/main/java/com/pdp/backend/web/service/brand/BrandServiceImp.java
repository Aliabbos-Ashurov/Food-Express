package com.pdp.backend.web.service.brand;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrandServiceImp {
    private static BrandServiceImp instance;

    public static BrandServiceImp getInstance() {
        if (instance == null) {
            instance = new BrandServiceImp();
        }
        return instance;
    }
}
