package com.pdp.web.repository.deliverer;

import com.pdp.web.model.deliverer.Deliverer;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Path;
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
    private static JsonSerializer<Deliverer> jsonSerializer;
    private static volatile DelivererRepository instance;

    private DelivererRepository() {
    }

    public static DelivererRepository getInstance() {
        if (instance == null) {
            synchronized (DelivererRepository.class) {
                if (instance == null) {
                    instance = new DelivererRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(JsonFilePath.PATH_DELIVERER));
                }
            }
        }
        return instance;
    }

    @Override
    public boolean add(@NonNull Deliverer deliverer) {
        List<Deliverer> deliverers = load();
        deliverers.add(deliverer);
        save(deliverers);
        return true;
    }

    @Override
    public boolean remove(UUID id) {
        List<Deliverer> deliverers = load();
        boolean removed = deliverers.removeIf(deliverer -> deliverer.getId().equals(id));
        if (removed) save(deliverers);
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
        List<Deliverer> deliverers = load();
        return deliverers.stream()
                .filter(deliverer -> deliverer.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Deliverer> getAll() {
        return load();
    }

    /**
     * Populates the list of {@code Deliverer} entities from persistent storage.
     *
     * @return A list containing the loaded deliverers, or an empty list in case of an error.
     */
    @SneakyThrows
    @Override
    public List<Deliverer> load() {
        return jsonSerializer.read(Deliverer.class);
    }

    /**
     * Persists the current list of deliverers to the permanent storage.
     *
     * @throws IOException if a problem occurs during writing to storage.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<Deliverer> deliverers) {
        jsonSerializer.write(deliverers);
    }
}