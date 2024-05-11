package com.pdp.controller;

import com.pdp.web.enums.role.Role;
import com.pdp.web.model.user.User;

/**
 * @author Doniyor Nishonov
 * Date: 11/May/2024  14:37
 **/
public class UserController {
    public static User curUser;

    public static void startApp() {
        if (LoginController.userSignInSignUp())
            afterLog();
        else startApp();
    }

    private static void afterLog() {
        if (curUser.getRole().equals(Role.USER)) {
            while (true) {
                CustomerOrderController.start();
            }
        } else if (curUser.getRole().equals(Role.DELIVERER)) {
            while (true) {

            }
        }
    }
}
