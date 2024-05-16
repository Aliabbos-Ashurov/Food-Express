package com.pdp.web.repository.user;

import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.user.User;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Handles storage, retrieval, and manipulation of {@link User} entities in a JSON serialized file.
 * Implements the {@link BaseRepository} interface for CRUD operations.
 * <p>
 * This repository uses a local JSON file as its data store, and performs operations such as add, remove,
 * find, and list all users. All changes to the repository are immediately saved to the file.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 17:06
 */
public class UserRepository implements BaseRepository<User, List<User>> {
    private static volatile UserRepository instance;
    private static JsonSerializer<User> jsonSerializer;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_USER));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new {@link User} to the repository and persists the changes to the data file.
     *
     * @param user The user to add.
     * @return Always returns {@code true} after adding and saving the user.
     */
    @Override
    public boolean add(@NonNull User user) {
        List<User> users = load();
        users.add(user);
        save(users);
        return true;
    }

    /**
     * Removes a user from the repository by their ID and persists the changes if the user was found and removed.
     *
     * @param id The unique ID of the user to remove.
     * @return {@code true} if a user was found and removed, {@code false} otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<User> users = load();
        boolean removed = users.removeIf(user -> user.getId().equals(id));
        if (removed) save(users);
        return removed;
    }

    /**
     * Finds and returns a user by their ID.
     *
     * @param id The unique ID of the user to find.
     * @return The found user or {@code null} if no user with the given ID exists.
     */
    @Override
    public User findById(@NonNull UUID id) {
        List<User> people = load();
        return people.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns an unmodifiable list of all users currently in the repository.
     *
     * @return An unmodifiable list view of users.
     */
    @Override
    public List<User> getAll() {
        return load();
    }

    /**
     * Loads the list of users from the data file into the repository's memory.
     *
     * @return A list of users; if the file does not exist or an error occurs during reading, returns an empty list.
     */
    @SneakyThrows
    @Override
    public List<User> load() {
        return jsonSerializer.read(User.class);
    }

    /**
     * Persists the current state of users to the data file.
     *
     * @throws IOException if an I/O error occurs while writing the data.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<User> users) {
        jsonSerializer.write(users);
    }
}
