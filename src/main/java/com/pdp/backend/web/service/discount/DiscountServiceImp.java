package com.pdp.backend.web.service.discount;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiscountServiceImp {
    private static DiscountServiceImp instance;

    public static DiscountServiceImp getInstance() {
        if (instance == null) {
            instance = new DiscountServiceImp();
        }
        return instance;
    }
}
