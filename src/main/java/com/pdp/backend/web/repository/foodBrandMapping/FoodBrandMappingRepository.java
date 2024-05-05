package com.pdp.backend.web.repository.foodBrandMapping;

import com.pdp.backend.web.config.path.ResourcePath;
import com.pdp.backend.web.model.foodBrandMapping.FoodBrandMapping;
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
 * This repository manages the persistent storage and retrieval of relationships between Food items and their Brands,
 * facilitating mapping operations by providing an abstracted interface to add, remove, and query FoodBrandMapping entities.
 * Serialization and deserialization are handled by JsonSerializer, leveraging JSON format for long-term data management.
 *
 * Extends the functionality of the {@link BaseRepository} to accommodate the specialized requirements of FoodBrandMapping entities,
 * enforcing consistent interaction patterns and behavior across the application.
 *
 * @see BaseRepository
 * @see JsonSerializer
 * @author Aliabbos Ashurov
 * @since 04/May/2024 16:54
 */
public class FoodBrandMappingRepository implements BaseRepository<FoodBrandMapping> {
    private final JsonSerializer<FoodBrandMapping> jsonSerializer;
    private final List<FoodBrandMapping> foodBrandMappings;

    /**
     * Initializes the repository, setting up the jsonSerializer with the path to the
     * food-brand mappings data file and loads existing mappings from persistent storage.
     */
    public FoodBrandMappingRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(ResourcePath.FOOD_BRAND_MAPPING));
        this.foodBrandMappings = load();
    }

    /**
     * {@inheritDoc}
     * Additionally, saves the updated list to the JSON storage immediately after adding.
     */
    @Override
    public boolean add(FoodBrandMapping foodBrandMapping) {
        foodBrandMappings.add(foodBrandMapping);
        save();
        return true;
    }

    /**
     * {@inheritDoc}
     * Once an object is removed, the changes are persisted to the JSON storage.
     */
    @Override
    public boolean remove(UUID id) {
        boolean removed = foodBrandMappings.removeIf(foodBrandMapping -> foodBrandMapping.getId().equals(id));
        if (removed) save();
        return removed;
    }

    /**
     * {@inheritDoc}
     * Retrieves a mapping by UUID if it exists, null otherwise.
     */
    @Override
    public FoodBrandMapping findById(UUID id) {
        return foodBrandMappings.stream()
                .filter(foodBrandMapping -> foodBrandMapping.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<FoodBrandMapping> getAll() {
        return Collections.unmodifiableList(foodBrandMappings);
    }

    @Override
    public List<FoodBrandMapping> load() {
        try {
            return jsonSerializer.read(FoodBrandMapping.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(foodBrandMappings);
    }
}
