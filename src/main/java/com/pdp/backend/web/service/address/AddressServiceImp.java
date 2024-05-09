package com.pdp.backend.web.service.address;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressServiceImp {
    private static AddressServiceImp instance;

    public static AddressServiceImp getInstance() {
        if (instance == null)
            instance=new AddressServiceImp();
        return instance;
    }
}
