package com.pdp.web.service.login;

import com.pdp.dto.LoginDTO;
import com.pdp.web.model.user.User;

/**
 * Interface for defining the operations for user authentication including login checks and signup processes.
 * It provides methods to verify login credentials and register new users into the system.
 *
 * @author Aliabbos Ashurov
 * @since 09/May/2024  15:22
 */
public interface LoginService {

    /**
     * Validates the login credentials provided in the {@link LoginDTO} and retrieves the corresponding user.
     *
     * @param dto The data transfer object containing the username and password for authentication.
     * @return The {@link User} object if the credentials are valid, or {@code null} if authentication fails.
     */
    User checkUser(LoginDTO dto);

    /**
     * Registers a new user into the system.
     *
     * @param user The {@link User} object containing the user's details for signup.
     * @return {@code true} if the registration is successful, {@code false} otherwise.
     */
    boolean signUp(User user);
}
