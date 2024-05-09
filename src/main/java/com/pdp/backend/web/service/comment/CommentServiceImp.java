package com.pdp.backend.web.service.comment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentServiceImp {
    private static CommentServiceImp instance;

    public static CommentServiceImp getInstance() {
        if (instance == null) {
            instance = new CommentServiceImp();
        }
        return instance;
    }
}
