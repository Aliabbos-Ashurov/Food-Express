package com.pdp.web.repository.user;

import com.pdp.config.SQLConfiguration;
import com.pdp.enums.Language;
import com.pdp.enums.role.Role;
import com.pdp.web.model.user.User;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a new {@link User} to the repository and persists the changes to the data file.
     *
     * @param user The user to add.
     * @return Always returns {@code true} after adding and saving the user.
     */
    @SneakyThrows
    @Override
    public boolean add(@NonNull User user) {
        return Objects.nonNull(sql.executeQuery("SELECT web.add_user(?,?,?);",
                user.getFullname(), user.getUsername(), user.getPassword(), user.getLanguage()));
    }

    /**
     * Removes a user from the repository by their ID and persists the changes if the user was found and removed.
     *
     * @param id The unique ID of the user to remove.
     * @return {@code true} if a user was found and removed, {@code false} otherwise.
     */
    @SneakyThrows
    @Override
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.auth_user WHERE id = ?;", id) > 0;
    }

    @Override
    @SneakyThrows
    public boolean update(@NonNull User user) {
        return sql.executeUpdate("UPDATE web.auth_user set full_name=?,user_name=?,password=?,phone_number=?,email=?,is_number_verified=?,is_email.verified=?,role=?,language=?,profile_picture_url=? WHERE id = ?;",
                user.getFullname(), user.getUsername(), user.getPassword(), user.getPhoneNumber(), user.getEmail(), user.isNumberVerified(),
                user.isEmailVerified(), String.valueOf(user.getRole()), String.valueOf(user.getLanguage()), user.getProfilePictureUrl()) > 0;
    }

    /**
     * Finds and returns a user by their ID.
     *
     * @param id The unique ID of the user to find.
     * @return The found user or {@code null} if no user with the given ID exists.
     */
    @Override
    public User findById(@NonNull UUID id) {
        return getAll().stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Returns an unmodifiable list of all users currently in the repository.
     *
     * @return An unmodifiable list view of users.
     */
    @SneakyThrows
    @Override
    public List<User> getAll() {
        ResultSet resultSet = sql.executeQuery("SELECT * FROM web.auth_user;");
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(UUID.fromString(resultSet.getString("id")));
            user.setFullname(resultSet.getString("full_name"));
            user.setUsername(resultSet.getString("user_name"));
            user.setPassword(resultSet.getString("password"));
            user.setPhoneNumber(resultSet.getString("phone_number"));
            user.setEmail(resultSet.getString("email"));
            user.setNumberVerified(resultSet.getBoolean("is_number_verified"));
            user.setEmailVerified(resultSet.getBoolean("is_email_verified"));
            user.setRole(Role.valueOf(resultSet.getString("role")));
            user.setLanguage(Language.valueOf(resultSet.getString("language")));
            user.setProfilePictureUrl(resultSet.getString("profile_picture_url"));
            user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            users.add(user);
        }
        return users;
    }
}
