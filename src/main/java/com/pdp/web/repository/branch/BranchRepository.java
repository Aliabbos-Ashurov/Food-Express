package com.pdp.web.repository.branch;

import com.pdp.web.model.branch.Branch;
import com.pdp.web.repository.BaseRepository;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Implements a repository for managing Branch entities, providing CRUD operations,
 * such as addition, removal, searching by ID, and retrieval of all branches.
 * <p>
 * This class interacts with the JsonSerializer for serializing and deserializing
 * Branch objects to a JSON formatted storage, making the data persistent across
 * application sessions.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 15:48
 */
public class BranchRepository implements BaseRepository<Branch, List<Branch>> {
    private static volatile BranchRepository instance;
    private static JsonSerializer<Branch> jsonSerializer;

    private BranchRepository() {
    }

    public static BranchRepository getInstance() {
        if (instance == null) {
            synchronized (BranchRepository.class) {
                if (instance == null) {
                    instance = new BranchRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_BRANCH));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new Branch entity to the repository and serializes the updated list
     * to permanent storage through {@link JsonSerializer}.
     * <p>
     * This method will add the Branch to an in-memory list and save the entire list
     * to the path defined by {@code PATH_BRANCH}.
     *
     * @param branch The Branch entity to be added to the repository.
     * @return True if the Branch has been successfully added, false otherwise.
     * @throws Exception If an error occurs during the saving process.
     */
    @Override
    public boolean add(@NonNull Branch branch) {
        List<Branch> branches = load();
        branches.add(branch);
        save(branches);
        return true;
    }

    /**
     * Removes the Branch with the specified UUID identifier from the repository
     * and persists the updated list.
     *
     * @param id The UUID of the Branch object to remove.
     * @return True if successful, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<Branch> branches = load();
        boolean removed = branches.removeIf((branch -> branch.getId().equals(id)));
        if (removed) save(branches);
        return removed;
    }

    /**
     * Retrieves the Branch with the specified UUID identifier from the repository.
     *
     * @param id The UUID to search for.
     * @return The Branch object if found, null otherwise.
     */
    @Override
    public Branch findById(@NonNull UUID id) {
        List<Branch> branches = load();
        return branches.stream()
                .filter((branch -> branch.getId().equals(id)))
                .findFirst()
                .orElse(null);
    }


    @Override
    public List<Branch> getAll() {
        return load();
    }

    /**
     * Loads the existing Branch entities from the permanent storage into memory.
     * <p>
     * Branch objects are read from a JSON formatted file using {@link JsonSerializer},
     * which converts the raw data into a list of Branch entities.
     *
     * @return A list of deserialized Branch objects.
     * @throws Exception If an error occurs during the reading and deserialization process.
     */
    @SneakyThrows
    @Override
    public List<Branch> load() {
        return jsonSerializer.read(Branch.class);
    }

    /**
     * Saves the current list of Branch entities to permanent storage.
     * <p>
     * The in-memory list of Branches is serialized to a JSON formatted file
     * by {@link JsonSerializer} ensuring that updates are persisted.
     *
     * @param list The list of Branch entities to be serialized and saved.
     * @throws Exception If an error occurs during serialization or writing to storage.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<Branch> list) {
        jsonSerializer.write(list);
    }
}
