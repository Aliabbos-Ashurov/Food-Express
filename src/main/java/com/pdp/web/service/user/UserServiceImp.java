package com.pdp.web.service.user;

import com.pdp.utils.PasswordEncoderUtils;
import com.pdp.utils.Validator;
import com.pdp.web.model.user.User;
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
    public boolean update(User user) {
        List<User> users = getAll();
        return users.stream()
                .filter(o -> o.getId().equals(user.getId()))
                .peek((o) -> {
                    o.setRole(user.getRole());
                    o.setPassword(user.getPassword());
                    o.setEmail(user.getEmail());
                    o.setLanguage(user.getLanguage());
                    o.setPhoneNumber(user.getPhoneNumber());
                    o.setFullname(user.getFullname());
                    o.setEmailVerified(user.isEmailVerified());
                    o.setNumberVerified(user.isNumberVerified());
                    o.setUsername(user.getUsername());
                    o.setProfilePictureUrl(user.getProfilePictureUrl());
                })
                .findFirst()
                .isPresent();
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
