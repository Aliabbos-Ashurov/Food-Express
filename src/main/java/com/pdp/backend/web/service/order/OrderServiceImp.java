package com.pdp.backend.web.service.order;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderServiceImp {
    private static OrderServiceImp instance;

    public static OrderServiceImp getInstance() {
        if (instance == null) {
            instance = new OrderServiceImp();
        }
        return instance;
    }
}
