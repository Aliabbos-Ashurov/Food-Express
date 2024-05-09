package com.pdp.backend.web.service.deliverer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DelivererServiceImp {
    private static DelivererServiceImp instance;

    public static DelivererServiceImp getInstance() {
        if (instance == null) {
            instance = new DelivererServiceImp();
        }
        return instance;
    }
}
