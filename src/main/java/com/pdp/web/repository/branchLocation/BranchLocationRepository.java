package com.pdp.web.repository.branchLocation;

import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.branchLocation.BranchLocation;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Repository for managing {@link BranchLocation} entities.
 * Implements {@link BaseRepository} to provide CRUD operations for branch locations.
 * This repository interacts with a {@link JsonSerializer} to serialize and
 * deserialize {@link BranchLocation} objects to and from a storage file specified by
 * {@link JsonFilePath#PATH_BRANCH_LOCATION}.
 *
 * @author Aliabbos Ashurov
 * @since 11/May/2024 10:01
 */
public class BranchLocationRepository implements BaseRepository<BranchLocation, List<BranchLocation>> {

    private static volatile BranchLocationRepository instance;
    private static JsonSerializer<BranchLocation> jsonSerializer;

    private BranchLocationRepository() {
    }

    /**
     * Returns the singleton instance of {@link BranchLocationRepository},
     * instantiating it if it does not yet exist. The instance retrieval is
     * thread-safe.
     *
     * @return The single instance of {@link BranchLocationRepository}.
     */
    public static BranchLocationRepository getInstance() {
        if (instance == null) {
            synchronized (BranchLocationRepository.class) {
                if (instance == null) {
                    instance = new BranchLocationRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(JsonFilePath.PATH_BRANCH_LOCATION));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new {@link BranchLocation} to the repository. This involves
     * adding it to the in-memory list and persisting this list to the filesystem.
     * This method is thread-safe.
     *
     * @param branchLocation The new {@link BranchLocation} to add.
     * @return {@code true} if the branch location is added and persisted successfully.
     */
    @Override
    public boolean add(@NonNull BranchLocation branchLocation) {
        List<BranchLocation> branchLocations = load();
        branchLocations.add(branchLocation);
        save(branchLocations);
        return true;
    }

    /**
     * Removes a {@link BranchLocation} from the repository identified by the
     * provided UUID. This will update the in-memory list and persist the change.
     *
     * @param id The UUID of the {@link BranchLocation} to remove.
     * @return {@code true} if the branch location is found and removed.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<BranchLocation> branchLocations = load();
        boolean removed = branchLocations.removeIf(branchLocation -> branchLocation.getId().equals(id));
        if (removed) save(branchLocations);
        return removed;
    }

    /**
     * Retrieves a {@link BranchLocation} by its UUID. If no matching
     * branch location is found, returns {@code null}.
     *
     * @param id The UUID of the {@link BranchLocation} to find.
     * @return The found {@link BranchLocation} or {@code null} if not found.
     */
    @Override
    public BranchLocation findById(@NonNull UUID id) {
        List<BranchLocation> branchLocations = load();
        return branchLocations.stream()
                .filter(branchLocation -> branchLocation.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtains all branch locations maintained by this repository.
     *
     * @return A list of {@link BranchLocation} objects.
     */
    @Override
    public List<BranchLocation> getAll() {
        return load();
    }

    /**
     * Loads the list of branch locations from the storage file into memory.
     * This method may throw unchecked exceptions as indicated by {@link SneakyThrows}.
     *
     * @return A list of deserialized {@link BranchLocation} objects.
     */
    @SneakyThrows
    @Override
    public List<BranchLocation> load() {
        return jsonSerializer.read(BranchLocation.class);
    }

    /**
     * Persists the current list of branch locations into the storage file.
     * This method may throw unchecked exceptions as indicated by {@link SneakyThrows}.
     *
     * @param branchLocations The list of {@link BranchLocation} to serialize and save.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<BranchLocation> branchLocations) {
        jsonSerializer.write(branchLocations);
    }
}
