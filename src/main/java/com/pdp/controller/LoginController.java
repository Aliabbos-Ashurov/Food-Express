package com.pdp.controller;

import com.pdp.dto.LoginDTO;
import com.pdp.java.console.NotificationHandler;
import com.pdp.java.console.Scan;
import com.pdp.utils.MenuUtils;
import com.pdp.web.model.user.User;
import com.pdp.web.service.login.LoginService;
import com.pdp.web.service.login.LoginServiceImpl;

import static com.pdp.java.console.Scan.*;

/**
 * @author Doniyor Nishonov
 * Date: 10/May/2024  20:59
 **/
public class LoginController {
    private static final LoginService loginService = LoginServiceImpl.getInstance();

    public static boolean userSignInSignUp() {
        while (true) {
            MenuUtils.menu(MenuUtils.MENU);
            switch (Scan.scanInt()) {
                case 1 -> {
                    return signIn();
                }
                case 2 -> {
                    return signUp();
                }
                case 0 -> {
                    System.exit(0);
                    return false;
                }
            }
        }
    }


    private static boolean signIn() {
        String username = scanStr("Username");
        String password = scanStr("Password");
        User user = loginService.checkUser(new LoginDTO(username, password));
        boolean isSuccessful = user != null;
        NotificationHandler.notifyAction("User", "find", isSuccessful);
        return isSuccessful;
    }

    private static boolean signUp() {
        String fullname = scanStr("Enter fullname");
        String username = scanStr("Enter username");
        String password = scanStr("Enter password");
        User user = User.builder()
                .fullname(fullname)
                .username(username)
                .password(password)
                .build();
        boolean isWorked = loginService.signUp(user);
        NotificationHandler.notifyAction("User", "added", isWorked);
        return isWorked;
    }
}
