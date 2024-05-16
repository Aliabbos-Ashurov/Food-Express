package com.pdp.web.repository.description;

import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.description.Description;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
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
    private static JsonSerializer<Description> jsonSerializer;
    private static volatile DescriptionRepository instance;

    /**
     * Initializes the repository by setting up the JsonSerializer and loads
     * existing descriptions from the JSON file located at the path specified by
     * JsonFilePath.DESCRIPTION.
     */
    private DescriptionRepository() {
    }

    /**
     * Retrieves the singleton instance of the {@code DescriptionRepository} class, creating it
     * if necessary. This method is thread-safe.
     *
     * @return The single global instance of {@code DescriptionRepository}.
     */
    public static DescriptionRepository getInstance() {
        if (instance == null) {
            synchronized (DescriptionRepository.class) {
                if (instance == null) {
                    instance = new DescriptionRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(JsonFilePath.PATH_DESCRIPTION));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new {@link Description} to the repository and updates the JSON storage file to reflect
     * this addition.
     *
     * @param description The {@code Description} entity to be added.
     * @return {@code true} after the entity is added and the storage file is updated.
     */
    @Override
    public boolean add(@NotNull Description description) {
        List<Description> descriptions = load();
        descriptions.add(description);
        save(descriptions);
        return true;
    }

    /**
     * Removes a {@link Description} entity from the repository using the provided UUID.
     * Reflects changes immediately to the JSON storage file.
     *
     * @param id The UUID of the {@code Description} entity to remove.
     * @return {@code true} if the entity is successfully found and removed; {@code false} otherwise.
     */
    @Override
    public boolean remove(@NotNull UUID id) {
        List<Description> descriptions = load();
        boolean removed = descriptions.removeIf(description -> description.getId().equals(id));
        if (removed) save(descriptions);
        return removed;
    }

    /**
     * Looks for a {@link Description} entity within the repository matching the provided UUID.
     *
     * @param id The UUID to locate the {@code Description} entity by.
     * @return The found {@code Description} entity if present; otherwise, {@code null}.
     */
    @Override
    public Description findById(@NotNull UUID id) {
        List<Description> descriptions = load();
        return descriptions.stream()
                .filter(description -> description.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Acquires all {@link Description} entities currently available in the repository.
     *
     * @return A {@link List} comprising all current {@code Description} entities.
     */
    @Override
    public List<Description> getAll() {
        return load();
    }

    /**
     * Reads the contents of the predefined JSON storage file and deserializes the data into a {@link List}
     * of {@link Description} entities.
     *
     * @return A {@link List} of {@code Description} entities.
     * @throws IOException If any reading errors occur while accessing the JSON file.
     */
    @SneakyThrows
    @Override
    public List<Description> load() {
        return jsonSerializer.read(Description.class);
    }

    @SneakyThrows
    @Override
    public void save(@NotNull List<Description> descriptions) {
        jsonSerializer.write(descriptions);
    }
}
