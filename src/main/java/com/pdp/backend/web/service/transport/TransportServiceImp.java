package com.pdp.backend.web.service.transport;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransportServiceImp {
    private static TransportServiceImp instance;

    public static TransportServiceImp getInstance() {
        if (instance == null) {
            instance = new TransportServiceImp();
        }
        return instance;
    }
}
