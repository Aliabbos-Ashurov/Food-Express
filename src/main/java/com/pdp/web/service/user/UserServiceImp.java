package com.pdp.web.service.user;

import com.pdp.utils.PasswordEncoderUtils;
import com.pdp.utils.Validator;
import com.pdp.web.model.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
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

    /**
     * Retrieves the singleton instance of UserServiceImp.
     *
     * @return The singleton instance of UserServiceImp.
     */
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
    public boolean add(@NotNull User user) {
        user.setPassword(PasswordEncoderUtils.encode(user.getPassword()));
        return repository.add(user);
    }

    /**
     * Removes a user from the repository using its UUID.
     *
     * @param id The UUID of the user to be removed.
     * @return true if the user was successfully removed, false otherwise.
     */

    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing user in the repository.
     *
     * @param user The User object with updated information.
     * @return true if the user was successfully updated, false otherwise.
     */
    @Override
    public boolean update(@NotNull User user) {
        List<User> users = getAll();
        Optional<User> userOptional = users.stream()
                .filter(o -> o.getId().equals(user.getId()))
                .peek((o) -> {
                    updateUserData(o, user);
                })
                .findFirst();
        if (userOptional.isPresent()) repository.save(users);
        return userOptional.isPresent();
    }

    private void updateUserData(User existingUser, User newUser) {
        existingUser.setRole(newUser.getRole());
        existingUser.setPassword(newUser.getPassword());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setLanguage(newUser.getLanguage());
        existingUser.setPhoneNumber(newUser.getPhoneNumber());
        existingUser.setFullname(newUser.getFullname());
        existingUser.setEmailVerified(newUser.isEmailVerified());
        existingUser.setNumberVerified(newUser.isNumberVerified());
        existingUser.setUsername(newUser.getUsername());
        existingUser.setProfilePictureUrl(newUser.getProfilePictureUrl());
    }

    /**
     * Searches for users that match a given query string.
     *
     * @param query The query string to match against user usernames.
     * @return A list of User objects that match the query.
     */
    @Override
    public List<User> search(String query) {
        List<User> users = getAll();
        return users.stream()
                .filter(user -> Validator.isValid(user.getUsername(), query))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a user by its UUID.
     *
     * @param id The UUID of the user to retrieve.
     * @return The User object, or null if no user is found.
     */
    @Override
    public User getByID(UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return A list of all User objects.
     */
    @Override
    public List<User> getAll() {
        return repository.getAll();
    }
}
