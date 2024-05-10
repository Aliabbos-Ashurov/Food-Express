package com.pdp.backend.web.repository.description;

import com.pdp.backend.web.model.description.Description;
import com.pdp.backend.web.repository.BaseRepository;
import com.pdp.json.serializer.JsonSerializer;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Facilitates the storage management of Description entities, allowing operations such
 * as add, remove, findById, and getAll. Interactions with the JSON data storage are
 * accomplished using JsonSerializer, permitting the persistence and retrieval of
 * Description records.
 * <p>
 * Inherits the BaseRepository interface's methods for standardized data access and manipulation.
 * This repository ensures all Description objects are up-to-date and synchronized with a
 * JSON-formatted data file identified by JsonFilePath.DESCRIPTION.
 *
 * @author Aliabbos Ashurov
 * @see BaseRepository
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

    public static DescriptionRepository getInstance() {
        if (instance == null) {
            synchronized (DescriptionRepository.class) {
                if (instance == null) {
                    instance = new DescriptionRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_DESCRIPTION));
                }
            }
        }
        return instance;
    }

    @Override
    public boolean add(Description description) {
        List<Description> descriptions = load();
        descriptions.add(description);
        save(descriptions);
        return true;
    }

    @Override
    public boolean remove(UUID id) {
        List<Description> descriptions = load();
        boolean removed = descriptions.removeIf(description -> description.getId().equals(id));
        if (removed) save(descriptions);
        return removed;
    }

    @Override
    public Description findById(UUID id) {
        List<Description> descriptions = load();
        return descriptions.stream()
                .filter(description -> description.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Description> getAll() {
        return load();
    }

    @SneakyThrows
    @Override
    public List<Description> load() {
        return jsonSerializer.read(Description.class);
    }

    @SneakyThrows
    @Override
    public void save(List<Description> descriptions) {
        jsonSerializer.write(descriptions);
    }
}
