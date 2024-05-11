package com.pdp.web.repository.branchLocation;

import com.pdp.web.model.branchLocation.BranchLocation;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Repository for managing {@link BranchLocation} entities.
 * Implements {@link BaseRepository} to provide CRUD operations for branch locations.
 *
 * This repository handles operations to persist and retrieve branch location data.
 *
 * @author Aliabbos Ashurov
 * @since 11/May/2024 10:01
 */
public class BranchLocationRepository implements BaseRepository<BranchLocation, List<BranchLocation>> {

    private static volatile BranchLocationRepository instance;
    private static JsonSerializer<BranchLocation> jsonSerializer;

    private BranchLocationRepository() {}

    /**
     * Returns a singleton instance of the {@link BranchLocationRepository}.
     *
     * @return The singleton instance of {@link BranchLocationRepository}.
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

    @Override
    public boolean add(@NonNull BranchLocation branchLocation) {
        List<BranchLocation> branchLocations = load();
        branchLocations.add(branchLocation);
        save(branchLocations);
        return true;
    }

    @Override
    public boolean remove(@NonNull UUID id) {
        List<BranchLocation> branchLocations = load();
        boolean removed = branchLocations.removeIf(branchLocation -> branchLocation.getId().equals(id));
        if (removed) save(branchLocations);
        return removed;
    }

    @Override
    public BranchLocation findById(@NonNull UUID id) {
        List<BranchLocation> branchLocations = load();
        return branchLocations.stream()
                .filter(branchLocation -> branchLocation.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<BranchLocation> getAll() {
        return load();
    }

    @SneakyThrows
    @Override
    public List<BranchLocation> load() {
        return jsonSerializer.read(BranchLocation.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<BranchLocation> branchLocations) {
        jsonSerializer.write(branchLocations);
    }
}
