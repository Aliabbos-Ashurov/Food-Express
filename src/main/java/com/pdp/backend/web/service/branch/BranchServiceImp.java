package com.pdp.backend.web.service.branch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchServiceImp {
    private static BranchServiceImp instance;

    public static BranchServiceImp getInstance() {
        if (instance == null) {
            instance = new BranchServiceImp();
        }
        return instance;
    }
}
