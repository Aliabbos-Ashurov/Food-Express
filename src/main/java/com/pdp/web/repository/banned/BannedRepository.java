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
     * Lazily initializes and retrieves the sole instance of {@code BannedRepository}.
     * Ensures that the instance is created only once using a thread-safe double-checked
     * locking mechanism.
     *
     * @return the exclusive instance of the {@code BannedRepository}
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
     * Adds a {@code Banned} instance to the repository and serializes the updated list
     * of banned entities to persistent storage.
     *
     * @param banned the {@link Banned} instance to be added to the repository
     * @return {@code true} if the operation was successful, {@code false} otherwise
     */
    @Override
    public boolean add(@NonNull Banned banned) {
        List<Banned> banneds = load();
        banneds.add(banned);
        save(banneds);
        return true;
    }

    /**
     * Removes a {@code Banned} instance identified by the provided {@link UUID} from the
     * repository and serializes the updated list to persistent storage.
     *
     * @param id the unique identifier of the {@link Banned} instance to be removed
     * @return {@code true} if an instance was found and removed, {@code false} otherwise
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<Banned> banneds = load();
        boolean removed = banneds.removeIf((banned -> banned.getId().equals(id)));
        if (removed) save(banneds);
        return removed;
    }

    /**
     * Finds and returns a {@code Banned} instance in the repository, identified by its {@link UUID}.
     *
     * @param id the unique identifier of the {@link Banned} instance to be retrieved
     * @return the {@link Banned} instance if it exists, or {@code null} if it does not
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
     * Retrieves an immutable list of all instances of {@code Banned} currently stored in the repository.
     *
     * @return an immutable list of {@link Banned} instances
     */
    @Override
    public List<Banned> getAll() {
        return load();
    }

    /**
     * Loads a list of all {@code Banned} instances from persistent storage.
     *
     * @return a list containing all deserialized {@link Banned} instances
     * @throws Exception if an issue occurs during deserialization
     */
    @SneakyThrows
    @Override
    public List<Banned> load() {
        return jsonSerializer.read(Banned.class);
    }

    /**
     * Serializes and saves the current list of {@code Banned} instances to persistent storage.
     *
     * @param list the list of {@link Banned} instances to be serialized
     * @throws Exception if an issue occurs during serialization or writing storage
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<Banned> list) {
        jsonSerializer.write(list);
    }
}
