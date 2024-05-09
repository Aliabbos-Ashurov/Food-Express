package com.pdp.backend.web.repository.description;

import com.pdp.backend.web.model.description.Description;
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
 * Facilitates the storage management of Description entities, allowing operations such
 * as add, remove, findById, and getAll. Interactions with the JSON data storage are
 * accomplished using JsonSerializer, permitting the persistence and retrieval of
 * Description records.
 *
 * Inherits the BaseRepository interface's methods for standardized data access and manipulation.
 * This repository ensures all Description objects are up-to-date and synchronized with a
 * JSON-formatted data file identified by JsonFilePath.DESCRIPTION.
 * @see BaseRepository
 * @author Aliabbos Ashurov
 * @since 04/May/2024 16:44
 */

public class DescriptionRepository implements BaseRepository<Description,List<Description>> {
    private final JsonSerializer<Description> jsonSerializer;
    private final List<Description> descriptions;

    /**
     * Initializes the repository by setting up the JsonSerializer and loads
     * existing descriptions from the JSON file located at the path specified by
     * JsonFilePath.DESCRIPTION.
     */
    public DescriptionRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(PATH_DESCRIPTION));
        this.descriptions = load();
    }

    @Override
    public boolean add(Description description) {
        descriptions.add(description);
        save();
        return true;
    }

    @Override
    public boolean remove(UUID id) {
        boolean removed = descriptions.removeIf(description -> description.getId().equals(id));
        if (removed) save();
        return removed;
    }

    @Override
    public Description findById(UUID id) {
        return descriptions.stream()
                .filter(description -> description.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Description> getAll() {
        return descriptions;
    }

    @Override
    public List<Description> load() {
        try {
            return jsonSerializer.read(Description.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(descriptions);
    }
}
