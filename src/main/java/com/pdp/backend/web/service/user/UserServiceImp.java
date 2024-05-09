package com.pdp.backend.web.service.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceImp {
    private static UserServiceImp instance;

    public static UserServiceImp getInstance() {
        if (instance == null) {
            instance = new UserServiceImp();
        }
        return instance;
    }
}
