package com.pdp.backend.web.service.user;

import com.pdp.backend.utils.PasswordEncoderUtils;
import com.pdp.backend.utils.Validator;
import com.pdp.backend.web.model.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the UserService interface that provides user-related operations.
 * This class follows the Singleton pattern to ensure only one instance is used.
 * It includes methods to manage users such as adding, removing, updating, and searching by criteria.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceImp implements UserService {
    private static volatile UserServiceImp instance;

    public static UserServiceImp getInstance() {
        if (instance == null) {
            synchronized (UserServiceImp.class) {
                if (instance == null) {
                    instance = new UserServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new user with an encoded password to the repository.
     *
     * @param user The user to add, with their password in plain text.
     * @return {@code true} if the user was added successfully, {@code false} otherwise.
     */
    @Override
    public boolean add(User user) {
        user.setPassword(PasswordEncoderUtils.encode(user.getPassword()));
        return repository.add(user);
    }

    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    @Override
    public boolean update(User object) {
        return false;
    }

    @Override
    public List<User> search(String query) {
        List<User> users = getAll();
        return users.stream()
                .filter(user -> Validator.isValid(user.getUsername(), query))
                .collect(Collectors.toList());
    }

    @Override
    public User getByID(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }
}
