package com.pdp.web.repository.banned;

import com.pdp.config.SQLConfiguration;
import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.banned.Banned;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Manages the storage and retrieval of {@link Banned} entities, providing a way to
 * record and access the details of banned users. Utilizes a {@link JsonSerializer}
 * to handle serialization to and from JSON for persistence.
 * <p>
 * Implements the {@link BaseRepository} interface, offering basic CRUD
 * operations such as add, remove, findById, and getAll.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 15:43
 */
public class BannedRepository implements BaseRepository<Banned, List<Banned>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a {@code Banned} instance to the repository and serializes the updated list
     * of banned entities to persistent storage.
     *
     * @param banned the {@link Banned} instance to be added to the repository
     * @return {@code true} if the operation was successful, {@code false} otherwise
     */
    @Override
    @SneakyThrows
    public boolean add(@NonNull Banned banned) {
        return sql.executeUpdate("INSERT INTO web.banned(user_id,banned_at,description_id) VALUES(?,?,?);",
                banned.getUserID(), banned.getBannedAt(), banned.getDescriptionID()) > 0;
    }

    /**
     * Removes a {@code Banned} instance identified by the provided {@link UUID} from the
     * repository and serializes the updated list to persistent storage.
     *
     * @param id the unique identifier of the {@link Banned} instance to be removed
     * @return {@code true} if an instance was found and removed, {@code false} otherwise
     */
    @Override
    @SneakyThrows
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.banned WHERE id = ?;", id) > 0;
    }

    /**
     * Finds and returns a {@code Banned} instance in the repository, identified by its {@link UUID}.
     *
     * @param id the unique identifier of the {@link Banned} instance to be retrieved
     * @return the {@link Banned} instance if it exists, or {@code null} if it does not
     */
    @Override
    public Banned findById(@NonNull UUID id) {
        return getAll().stream()
                .filter((banned -> banned.getId().equals(id)))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves an immutable list of all instances of {@code Banned} currently stored in the repository.
     *
     * @return an immutable list of {@link Banned} instances
     */
    @Override
    @SneakyThrows
    public List<Banned> getAll() {
        ResultSet rs = sql.executeQuery("SELECT * FROM web.banned;");
        List<Banned> banneds = new ArrayList<>();
        while (rs.next()) {
            Banned banned = new Banned();
            banned.setId(UUID.fromString(rs.getString(1)));
            banned.setUserID(UUID.fromString(rs.getString(2)));
            banned.setBannedAt(rs.getTimestamp(3).toLocalDateTime());
            banned.setDescriptionID(UUID.fromString(rs.getString(4)));
            banned.setCreatedAt(rs.getTimestamp(5).toLocalDateTime());
            banneds.add(banned);
        }
        return banneds;
    }
}
