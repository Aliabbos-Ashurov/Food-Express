package com.pdp.web.service.picture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Singleton service class for managing picture entities.
 * Implements thread-safe lazy initialization with double-checked locking.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PictureServiceImp {
    private static volatile PictureServiceImp instance;

    /**
     * Retrieves the singleton instance of PictureServiceImp.
     *
     * @return The singleton instance of PictureServiceImp.
     */
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
