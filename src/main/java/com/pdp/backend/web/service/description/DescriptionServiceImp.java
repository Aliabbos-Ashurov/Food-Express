package com.pdp.backend.web.service.description;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DescriptionServiceImp {
    private static DescriptionServiceImp instance;

    public static DescriptionServiceImp getInstance() {
        if (instance == null) {
            instance = new DescriptionServiceImp();
        }
        return instance;
    }
}
