package com.pdp.controller;

import com.pdp.dto.LoginDTO;
import com.pdp.web.enums.role.Role;
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

    public static void signIn() {
        String username = scanStr("Username");
        String password = scanStr("Password");
        User user = loginService.checkUser(new LoginDTO(username, password));
    }

    public static void signUp() {
        String fullname = scanStr("Fullname");
        String username = scanStr("Username");
        String password = scanStr("Password");
        User user = User.builder()
                .fullname(fullname)
                .username(username)
                .password(password)
                .role(Role.USER)
                .build();
        boolean signUp = loginService.signUp(user);
        if (!signUp) System.out.println("Sign up failed");
    }
}
