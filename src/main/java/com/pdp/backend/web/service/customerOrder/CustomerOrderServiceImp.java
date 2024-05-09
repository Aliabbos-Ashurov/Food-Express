package com.pdp.backend.web.service.customerOrder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerOrderServiceImp {
    private static CustomerOrderServiceImp instance;

    public static CustomerOrderServiceImp getInstance() {
        if (instance == null) {
            instance = new CustomerOrderServiceImp();
        }
        return instance;
    }
}
