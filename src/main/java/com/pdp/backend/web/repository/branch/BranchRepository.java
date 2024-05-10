package com.pdp.backend.web.repository.branch;

import com.pdp.backend.web.model.branch.Branch;
import com.pdp.backend.web.repository.BaseRepository;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
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

    @SneakyThrows
    @Override
    public List<Branch> load() {
        return jsonSerializer.read(Branch.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<Branch> list) {
        jsonSerializer.write(list);
    }
}
