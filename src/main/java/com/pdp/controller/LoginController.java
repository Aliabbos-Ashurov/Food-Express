package com.pdp.controller;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.dto.LoginDTO;
import com.pdp.java.console.NotificationHandler;
import com.pdp.java.console.Scan;
import com.pdp.utils.MenuUtils;
import com.pdp.web.enums.Language;
import com.pdp.web.model.user.User;
import com.pdp.web.service.login.LoginService;
import com.pdp.web.service.user.UserService;

import java.util.Objects;

import static com.pdp.java.console.Scan.*;

/**
 * @author Doniyor Nishonov
 * Date: 10/May/2024  20:59
 **/
public class LoginController {
    private static final LoginService loginService = ThreadSafeBeansContainer.loginServiceThreadLocal.get();
    private static final UserService userService = ThreadSafeBeansContainer.userServiceThreadLocal.get();
    private static Language language;

    private static Language getLanguage() {
        while (true) {
            MenuUtils.menu(MenuUtils.LANGUAGE, Language.UZ);
            System.out.println("[1] - UZ\n[2] - EN\n[0] - EXIT");
            switch (scanInt()) {
                case 1 -> {
                    return Language.UZ;
                }
                case 0 -> {
                    System.exit(0);
                    return null;
                }
                default -> {
                    return Language.EN;
                }
            }
        }
    }

    public static boolean userSignInSignUp(boolean b) {
        if (b) language = getLanguage();
        while (true) {
            MenuUtils.menu(MenuUtils.SIGN_IN_UP, language);
            switch (Scan.scanInt()) {
                case 1 -> {
                    return signIn();
                }
                case 2 -> {
                    return signUp();
                }
                case 0 -> {
                    language = getLanguage();
                }
            }
        }
    }


    private static boolean signIn() {
        String username = scanStr("Username");
        String password = scanStr("Password");
        UserController.curUser = loginService.checkUser(new LoginDTO(username, password));
        boolean isSuccessful = UserController.curUser != null;
        if (isSuccessful) {
            UserController.curUser.setLanguage(language);
            userService.update(UserController.curUser);
        }
        NotificationHandler.notifyAction("User", "sign-in", isSuccessful);
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
                .language(language)
                .build();
        boolean isWorked = loginService.signUp(user);
        if (isWorked) UserController.curUser = user;
        NotificationHandler.notifyAction("User", "sing-up", isWorked);
        return isWorked;
    }

    public static Language getCurUserLanguage() {
        return language;
    }
}
