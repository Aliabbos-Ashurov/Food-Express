package com.pdp.backend.web.service.login;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Aliabbos Ashurov
 * @since 09/May/2024  15:22
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginServiceImpl {
    private static LoginServiceImpl instance;

    public static LoginServiceImpl getInstance() {
        if (instance == null) {
            instance = new LoginServiceImpl();
        }
        return instance;
    }
}
