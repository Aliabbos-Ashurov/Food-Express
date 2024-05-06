package com.pdp.backend.web.repository.user;

import com.pdp.backend.web.config.jsonFilePath.JsonFilePath;
import com.pdp.backend.web.model.user.User;
import com.pdp.backend.web.repository.BaseRepository;
import com.pdp.json.serializer.JsonSerializer;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Handles storage, retrieval, and manipulation of {@link User} entities in a JSON serialized file.
 * Implements the {@link BaseRepository} interface for CRUD operations.
 *
 * This repository uses a local JSON file as its data store, and performs operations such as add, remove,
 * find, and list all users. All changes to the repository are immediately saved to the file.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 17:06
 */
public class UserRepository implements BaseRepository<User> {
    private final JsonSerializer<User> jsonSerializer;
    private final List<User> users;

    /**
     * Initializes a new instance of the UserRepository, setting up the JSON serializer
     * with the path to the users data file and loading the current set of users from that file.
     */
    public UserRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(PATH_USER));
        this.users = load();
    }

    /**
     * Adds a new {@link User} to the repository and persists the changes to the data file.
     *
     * @param user The user to add.
     * @return Always returns {@code true} after adding and saving the user.
     */
    @Override
    public boolean add(User user) {
        users.add(user);
        save();
        return true;
    }

    /**
     * Removes a user from the repository by their ID and persists the changes if the user was found and removed.
     *
     * @param id The unique ID of the user to remove.
     * @return {@code true} if a user was found and removed, {@code false} otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        boolean removed = users.removeIf(user -> user.getId().equals(id));
        if (removed) save();
        return removed;
    }

    /**
     * Finds and returns a user by their ID.
     *
     * @param id The unique ID of the user to find.
     * @return The found user or {@code null} if no user with the given ID exists.
     */
    @Override
    public User findById(UUID id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Returns an unmodifiable list of all users currently in the repository.
     *
     * @return An unmodifiable list view of users.
     */
    @Override
    public List<User> getAll() {
        return Collections.unmodifiableList(users);
    }

    /**
     * Loads the list of users from the data file into the repository's memory.
     *
     * @return A list of users; if the file does not exist or an error occurs during reading, returns an empty list.
     */    @Override
    public List<User> load() {
        try {
            return jsonSerializer.read(User.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Persists the current state of users to the data file.
     *
     * @throws IOException if an I/O error occurs while writing the data.
     */
    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(users);
    }
}
