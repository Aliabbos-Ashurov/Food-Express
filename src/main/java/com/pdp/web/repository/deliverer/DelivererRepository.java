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
     * Finds and returns a {@code Deliverer} by its UUID from the local repository cache.
     * It uses a stream to filter the list of deliverers. If the deliverer is not found,
     * {@code null} is returned.
     *
     * @param id The UUID of the {@code Deliverer} to retrieve.
     * @return The matching {@code Deliverer} object if found; {@code null} otherwise.
     */
    @Override
    public Deliverer findById(UUID id) {
        List<Deliverer> deliverers = load();
        return deliverers.stream()
                .filter(deliverer -> deliverer.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Retrieves all {@code Deliverer} entities from the repository. The list is dynamically
     * loaded from the JSON storage upon each call to ensure up-to-date data is returned.
     *
     * @return A list of all {@code Deliverer} objects contained in the repository.
     */
    @Override
    public List<Deliverer> getAll() {
        return load();
    }

    /**
     * Loads and deserializes the list of {@code Deliverer} entities from the JSON storage file.
     * The {@link JsonSerializer} is used here to convert the contents of the file to a list of
     * {@code Deliverer} objects.
     *
     * @return A list containing all deserialized {@code Deliverer} entities from the file.
     * @throws IOException If any issues occur during file reading and deserialization.
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
