package com.pdp.backend.web.service.banned;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BannedServiceImp {
    private static BannedServiceImp instance;

    public static BannedServiceImp getInstance() {
        if (instance == null) instance = new BannedServiceImp();
        return instance;
    }
}
