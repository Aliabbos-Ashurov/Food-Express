package com.pdp.backend.web.repository.deliverer;

import com.pdp.backend.web.config.jsonFilePath.JsonFilePath;
import com.pdp.backend.web.model.deliverer.Deliverer;
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
public class DelivererRepository implements BaseRepository<Deliverer,List<Deliverer>> {
    private final JsonSerializer<Deliverer> jsonSerializer;
    private final List<Deliverer> deliverers;

    public DelivererRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(PATH_DELIVERER));
        this.deliverers = load();
    }

    @Override
    public boolean add(Deliverer deliverer) {
        deliverers.add(deliverer);
        save();
        return true;
    }

    @Override
    public boolean remove(UUID id) {
        boolean removed = deliverers.removeIf(deliverer -> deliverer.getId().equals(id));
        if (removed) save();
        return removed;
    }

    /**
     * Searches for a {@code Deliverer} in the repository based on a UUID.
     *
     * @param id The unique identifier of the deliverer to find.
     * @return The {@code Deliverer} object if present; {@code null} otherwise.
     */
    @Override
    public Deliverer findById(UUID id) {
        return deliverers.stream()
                .filter(deliverer -> deliverer.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Deliverer> getAll() {
        return Collections.unmodifiableList(deliverers);
    }

    /**
     * Populates the list of {@code Deliverer} entities from persistent storage.
     *
     * @return A list containing the loaded deliverers, or an empty list in case of an error.
     */
    @Override
    public List<Deliverer> load() {
        try {
            return jsonSerializer.read(Deliverer.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Persists the current list of deliverers to the permanent storage.
     *
     * @throws IOException if a problem occurs during writing to storage.
     */
    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(deliverers);
    }
}
