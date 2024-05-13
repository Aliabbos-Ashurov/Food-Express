package com.pdp.web.service.login;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.dto.LoginDTO;
import com.pdp.utils.PasswordEncoderUtils;
import com.pdp.web.model.user.User;
import com.pdp.web.service.user.UserService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Implements the login service, handling user authentication and registration.
 * This class follows the Singleton design pattern to ensure a single instance throughout the application.
 *
 * @author Aliabbos Ashurov
 * @see LoginService
 * @see UserService
 * @since 09/May/2024  15:22
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginServiceImpl implements LoginService {
    private static final UserService userService = ThreadSafeBeansContainer.userServiceThreadLocal.get();
    private static volatile LoginServiceImpl instance;

    /**
     * Retrieves the singleton instance of LoginServiceImpl.
     *
     * @return The singleton instance of LoginServiceImpl.
     */
    public static LoginServiceImpl getInstance() {
        if (instance == null) {
            synchronized (LoginServiceImpl.class) {
                if (instance == null) {
                    instance = new LoginServiceImpl();
                }
            }
        }
        return instance;
    }

    /**
     * Checks the provided login credentials against the existing users.
     *
     * @param dto Data transfer object containing username and password for authentication.
     * @return The authenticated User object or {@code null} if authentication fails.
     */
    @Override
    public User checkUser(LoginDTO dto) {
        List<User> users = userService.getAll();
        return users.stream()
                .filter(user -> user.getUsername().equals(dto.username())
                        && PasswordEncoderUtils.check(dto.password(), user.getPassword()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Registers a new user if the username is not already taken.
     *
     * @param user The User object containing information for registration.
     * @return {@code true} if registration is successful, {@code false} if the username is already in use.
     */
    @Override
    public boolean signUp(User user) {
        List<User> users = userService.getAll();
        boolean userExists = users.stream()
                .anyMatch(existingUser -> existingUser.getUsername().equals(user.getUsername()));
        if (!userExists) userService.add(user);
        return !userExists;
    }
}
