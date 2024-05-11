package com.pdp.controller;

import com.pdp.web.enums.role.Role;
import com.pdp.web.model.user.User;

/**
 * Controller to manage user interactions within the application.
 *
 * Date: 11/May/2024 14:37
 * @author Doniyor Nishonov
 **/
public class UserController {
    private static User currentUser;

    public static void startApplication() {
        boolean signedIn = LoginController.userSignInSignUp();
        if (signedIn) {
            handlePostLogin();
        } else {
            startApplication();
        }
    }

    private static void handlePostLogin() {
        if (currentUser.getRole() == Role.USER) {
            CustomerOrderController.start();
        } else if (currentUser.getRole() == Role.DELIVERER) {
            //...
        }
    }
}
