package com.pdp.web.service.picture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PictureServiceImp {
    private static volatile PictureServiceImp instance;

    public static PictureServiceImp getInstance() {
        if (instance == null) {
            synchronized (PictureServiceImp.class) {
                if (instance == null) {
                    instance = new PictureServiceImp();
                }
            }
        }
        return instance;
    }
}
