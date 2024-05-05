package com.pdp.backend.web.repository.brand;

import com.pdp.backend.web.config.path.ResourcePath;
import com.pdp.backend.web.model.brand.Brand;
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
 * Handles the persistence of Brand entities, providing a mechanism to add, remove,
 * find, and list all Brands. This repository leverages JsonSerializer for the
 * serialization and deserialization of Brand instances to and from JSON format.
 *
 * Implements the BaseRepository interface, utilizing a local in-memory list that
 * is synchronized with a JSON file defined by ResourcePath.BRAND.
 *
 * Operations to this repository are persisted to the JSON storage immediately.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 15:51
 */
public class BrandRepository implements BaseRepository<Brand> {
    private final JsonSerializer<Brand> jsonSerializer;
    private final List<Brand> brands;

    public BrandRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(ResourcePath.BRAND));
        this.brands = load();
    }

    /**
     * Adds a Brand to the repository and persists the change.
     *
     * @param brand The Brand to be added.
     * @return true if the addition is successful, false otherwise.
     */
    @Override
    public boolean add(Brand brand) {
        brands.add(brand);
        save();
        return true;
    }

    /**
     * Removes a Brand from the repository by its identifier and persists the change.
     *
     * @param id The UUID of the Brand to be removed.
     * @return true if the Brand is successfully removed, false otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        boolean removed = brands.removeIf((brand -> brand.getId().equals(id)));
        if (removed) save();
        return removed;
    }

    @Override
    public Brand findById(UUID id) {
        return brands.stream()
                .filter((brand -> brand.getId().equals(id)))
                .findFirst().orElse(null);
    }

    @Override
    public List<Brand> getAll() {
        return Collections.unmodifiableList(brands);
    }

    @Override
    public List<Brand> load() {
        try {
            return jsonSerializer.read(Brand.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(brands);
    }
}
