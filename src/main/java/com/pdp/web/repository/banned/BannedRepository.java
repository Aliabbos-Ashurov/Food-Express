package com.pdp.web.repository.banned;

import com.pdp.web.model.banned.Banned;
import com.pdp.web.repository.BaseRepository;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
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
    private static volatile BannedRepository instance;
    private static JsonSerializer<Banned> jsonSerializer;


    private BannedRepository() {
    }

    /**
     * Gets the singleton instance of BannedRepository.
     *
     * @return The singleton instance of BannedRepository.
     */
    public static BannedRepository getInstance() {
        if (instance == null) {
            synchronized (BannedRepository.class) {
                if (instance == null) {
                    instance = new BannedRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_BANNED));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new banned entity to the repository and persists changes.
     *
     * @param banned The {@link Banned} object to add.
     * @return True if the banned entity is added successfully, false otherwise.
     */
    @Override
    public boolean add(@NonNull Banned banned) {
        List<Banned> banneds = load();
        banneds.add(banned);
        save(banneds);
        return true;
    }

    /**
     * Removes a banned entity from the repository based on the given ID and persists changes.
     *
     * @param id The UUID of the banned entity to remove.
     * @return True if a banned entity with the specified ID was found and removed, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<Banned> banneds = load();
        boolean removed = banneds.removeIf((banned -> banned.getId().equals(id)));
        if (removed) save(banneds);
        return removed;
    }

    /**
     * Retrieves a banned entity based on its unique identifier.
     *
     * @param id The UUID of the banned entity to find.
     * @return The {@link Banned} entity if found; null otherwise.
     */
    @Override
    public Banned findById(@NonNull UUID id) {
        List<Banned> banneds = load();
        return banneds.stream()
                .filter((banned -> banned.getId().equals(id)))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets an unmodifiable list of all banned entities in the repository.
     *
     * @return An unmodifiable list representing all the banned entities.
     */
    @Override
    public List<Banned> getAll() {
        return load();
    }

    @SneakyThrows
    @Override
    public List<Banned> load() {
        return jsonSerializer.read(Banned.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<Banned> list) {
        jsonSerializer.write(list);
    }
}
