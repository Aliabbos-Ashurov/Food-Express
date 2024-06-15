package com.pdp.web.repository.transport;

import com.pdp.config.SQLConfiguration;
import com.pdp.web.model.transport.Transport;
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
 * Manages the persistence and retrieval of {@link Transport} objects using JSON serialization.
 * This class implements the {@link BaseRepository} interface to provide CRUD operations on
 * transport entities stored in a JSON file.
 * <p>
 * The repository is backed by a list that is synchronized with a local JSON file, allowing for
 * persistent storage and retrieval operations on Transport entities.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 17:03
 */
public class TransportRepository implements BaseRepository<Transport, List<Transport>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a {@link Transport} object to the repository and saves the updated list to the JSON file.
     *
     * @param transport The transport entity to add.
     * @return Always returns {@code true}, indicating the transport was added successfully.
     */
    @SneakyThrows
    @Override
    public boolean add(@NotNull Transport transport) {
        return sql.executeUpdate("INSERT INTO web.transport(deliverer_id,name,registered_number) VALUES (?,?,?);",
                transport.getDeliverID(), transport.getName(), transport.getRegisteredNumber()) > 0;
    }

    /**
     * Removes a transport entity by its UUID and saves the updated list if the entity was successfully removed.
     *
     * @param id The UUID of the transport entity to be removed.
     * @return {@code true} if the transport entity was found and removed, {@code false} otherwise.
     */
    @SneakyThrows
    @Override
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.transport WHERE id = ?;", id) > 0;
    }

    @Override
    @SneakyThrows
    public boolean update(@NonNull Transport transport) {
        return sql.executeUpdate("UPDATE web.transport set deliverer_id=?,name=?,registered_number=? WHERE id = ?",
                transport.getDeliverID(), transport.getName(), transport.getRegisteredNumber(), transport.getId()) > 0;
    }

    /**
     * Finds a transport entity by its UUID.
     *
     * @param id The UUID of the transport entity to find.
     * @return The transport entity if found, or {@code null} if no such entity exists.
     */
    @Override
    public Transport findById(@NonNull UUID id) {
        return getAll().stream().filter(transport -> transport.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Returns an unmodifiable list of all transport entities in the repository.
     *
     * @return An unmodifiable list of {@link Transport} objects.
     */
    @SneakyThrows
    @Override
    public List<Transport> getAll() {
        ResultSet resultSet = sql.executeQuery("SELECT * FROM web.transport;");
        List<Transport> transports = new ArrayList<>();
        while (resultSet.next()) {
            Transport transport = new Transport();
            transport.setId(UUID.fromString(resultSet.getString("id")));
            transport.setName(resultSet.getString("name"));
            transport.setDeliverID(UUID.fromString(resultSet.getString("deliverer_id")));
            transport.setRegisteredNumber(resultSet.getString("registered_number"));
            transport.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            transports.add(transport);
        }
        return transports;
    }
}
