package com.pdp.controller;

import com.pdp.enums.role.Role;
import com.pdp.web.model.user.User;

/**
 * Controller to manage user interactions within the application.
 * Date: 11/May/2024 14:37
 * @author Doniyor Nishonov
 **/
public class UserController {
    public static User curUser;

    public static void startApplication() {
        boolean signedIn = LoginController.userSignInSignUp(true);
        if (signedIn) {
            handlePostLogin();
        } else {
            startApplication();
        }
    }

    public static void handlePostLogin() {
        if (curUser.getRole() == Role.USER) {
            CustomerOrderController.start();
        }
        if (curUser.getRole() == Role.DELIVERER) {
            DelivererController.deliverer();
        }
    }
    public static void makeUserDataNull() {
        curUser.setLanguage(null);
        curUser.setPassword(null);
        curUser.setUsername(null);
        curUser.setRole(null);
        curUser.setFullname(null);
        curUser.setPhoneNumber(null);
        curUser.setEmail(null);
        curUser.setNumberVerified(false);
        curUser.setEmailVerified(false);
        curUser.setProfilePictureUrl(null);
    }
}
