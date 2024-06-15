package com.pdp.web.repository.description;

import com.pdp.config.SQLConfiguration;
import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.description.Description;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import sql.helper.SQLHelper;

import java.nio.file.Path;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The {@code DescriptionRepository} class provides a specialized storage mechanism
 * for {@link Description} entities. It supports the basic CRUD operations including addition,
 * deletion, searching by identifier, and obtaining all records.
 * <p>
 * Persistence of {@code Description} records is handled by {@link JsonSerializer} which facilitates
 * serialization to and from JSON format. As such, this repository provides a robust means of storing
 * Description data to a JSON file specified by {@link JsonFilePath#PATH_DESCRIPTION}.
 * <p>
 * Adheres to the {@link BaseRepository} interface for standard interaction with persistence layers.
 *
 * @author Aliabbos Ashurov
 * @see BaseRepository
 * @see JsonSerializer
 * @see JsonFilePath#PATH_DESCRIPTION
 * @since 04/May/2024 16:44
 */
public class DescriptionRepository implements BaseRepository<Description, List<Description>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a new {@link Description} to the repository and updates the JSON storage file to reflect
     * this addition.
     *
     * @param description The {@code Description} entity to be added.
     * @return {@code true} after the entity is added and the storage file is updated.
     */
    @SneakyThrows
    @Override
    public boolean add(@NotNull Description description) {
        return sql.executeUpdate("INSERT INTO web.description(name,text) VALUES (?,?);", description.getName(), description.getText()) > 0;
    }

    /**
     * Removes a {@link Description} entity from the repository using the provided UUID.
     * Reflects changes immediately to the JSON storage file.
     *
     * @param id The UUID of the {@code Description} entity to remove.
     * @return {@code true} if the entity is successfully found and removed; {@code false} otherwise.
     */
    @SneakyThrows
    @Override
    public boolean remove(@NotNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.description WHERE id = ?;", id) > 0;
    }

    @Override
    @SneakyThrows
    public boolean update(@NonNull Description description) {
        return sql.executeUpdate("UPDATE web.description set name = ?, text = ? WHERE id = ?;",
                description.getName(), description.getText(), description.getId()) > 0;
    }

    /**
     * Looks for a {@link Description} entity within the repository matching the provided UUID.
     *
     * @param id The UUID to locate the {@code Description} entity by.
     * @return The found {@code Description} entity if present; otherwise, {@code null}.
     */
    @Override
    public Description findById(@NotNull UUID id) {
        return getAll().stream()
                .filter(description -> description.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Acquires all {@link Description} entities currently available in the repository.
     *
     * @return A {@link List} comprising all current {@code Description} entities.
     */
    @SneakyThrows
    @Override
    public List<Description> getAll() {
        ResultSet resultSet = sql.executeQuery("SELECT * FROM web.description;");
        List<Description> descriptions = new ArrayList<>();
        while (resultSet.next()) {
            Description description = new Description();
            description.setId(UUID.fromString(resultSet.getString("id")));
            description.setName(resultSet.getString("name"));
            description.setText(resultSet.getString("text"));
            description.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            descriptions.add(description);
        }
        return descriptions;
    }
}
