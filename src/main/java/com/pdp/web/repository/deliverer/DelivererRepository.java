package com.pdp.web.repository.deliverer;

import com.pdp.config.SQLConfiguration;
import com.pdp.web.model.deliverer.Deliverer;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Repository for handling Deliverer entities. Offers basic CRUD operations and enforces
 * data persistence through JSON serialization thanks to the JsonSerializer.
 * <p>
 * Performs the addition, removal, search by unique identifier, and retrieval of the complete
 * list of Deliverer records, maintaining them in synchronized state with the storage system.
 *
 * @author Aliabbos Ashurov
 * @see BaseRepository
 * @since 04/May/2024 16:41
 */
public class DelivererRepository implements BaseRepository<Deliverer, List<Deliverer>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    @SneakyThrows
    @Override
    public boolean add(@NonNull Deliverer deliverer) {
        return sql.executeUpdate("INSERT INTO web.deliverer(full_name,phone_number) VALUES (?,?);", deliverer.getFullname(), deliverer.getPhoneNumber()) > 0;
    }

    @SneakyThrows
    @Override
    public boolean remove(@NotNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.deliverer WHERE id = ?;", id) > 0;
    }

    @Override
    @SneakyThrows
    public boolean update(@NonNull Deliverer deliverer) {
        return sql.executeUpdate("UPDATE wen.deliverer set full_name=?,phone_number=? WHERE id = ?;",
                deliverer.getFullname(), deliverer.getPhoneNumber(), deliverer.getId()) > 0;
    }

    /**
     * Finds and returns a {@code Deliverer} by its UUID from the local repository cache.
     * It uses a stream to filter the list of deliverers. If the deliverer is not found,
     * {@code null} is returned.
     *
     * @param id The UUID of the {@code Deliverer} to retrieve.
     * @return The matching {@code Deliverer} object if found; {@code null} otherwise.
     */
    @Override
    public Deliverer findById(@NotNull UUID id) {
        return getAll().stream()
                .filter(deliverer -> deliverer.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Retrieves all {@code Deliverer} entities from the repository. The list is dynamically
     * loaded from the JSON storage upon each call to ensure up-to-date data is returned.
     *
     * @return A list of all {@code Deliverer} objects contained in the repository.
     */
    @SneakyThrows
    @Override
    public List<Deliverer> getAll() {
        ResultSet resultSet = sql.executeQuery("SELECT * FROM web.deliverer;");
        List<Deliverer> deliverers = new ArrayList<>();
        while (resultSet.next()) {
            Deliverer deliverer = new Deliverer();
            deliverer.setId(resultSet.getObject("id", UUID.class));
            deliverer.setFullname(resultSet.getString("full_name"));
            deliverer.setPhoneNumber(resultSet.getString("phone_number"));
            deliverer.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            deliverers.add(deliverer);
        }
        return deliverers;
    }
}
