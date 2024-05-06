package com.pdp.backend.web.repository.branch;

import com.pdp.backend.web.config.jsonFilePath.JsonFilePath;
import com.pdp.backend.web.model.branch.Branch;
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
 * Implements a repository for managing Branch entities, providing CRUD operations,
 * such as addition, removal, searching by ID, and retrieval of all branches.
 *
 * This class interacts with the JsonSerializer for serializing and deserializing
 * Branch objects to a JSON formatted storage, making the data persistent across
 * application sessions.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 15:48
 */
public class BranchRepository implements BaseRepository<Branch> {
    private final JsonSerializer<Branch> jsonSerializer;
    private final List<Branch> branches;

    public BranchRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(PATH_BRANCH));
        this.branches = load();
    }

    @Override
    public boolean add(Branch branch) {
        branches.add(branch);
        save();
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
    public boolean remove(UUID id) {
        boolean removed = branches.removeIf((branch -> branch.getId().equals(id)));
        if (removed) save();
        return removed;
    }

    /**
     * Retrieves the Branch with the specified UUID identifier from the repository.
     *
     * @param id The UUID to search for.
     * @return The Branch object if found, null otherwise.
     */
    @Override
    public Branch findById(UUID id) {
        return branches.stream()
                .filter((branch -> branch.getId().equals(id)))
                .findFirst().orElse(null);
    }

    @Override
    public List<Branch> getAll() {
        return Collections.unmodifiableList(branches);
    }

    @Override
    public List<Branch> load() {
        try {
            return jsonSerializer.read(Branch.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(branches);
    }
}
