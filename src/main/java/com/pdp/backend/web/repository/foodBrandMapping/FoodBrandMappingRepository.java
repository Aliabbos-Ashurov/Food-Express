package com.pdp.backend.web.repository.foodBrandMapping;

import com.pdp.backend.web.model.foodBrandMapping.FoodBrandMapping;
import com.pdp.backend.web.repository.BaseRepository;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;


/**
 * This repository manages the persistent storage and retrieval of relationships between Food items and their Brands,
 * facilitating mapping operations by providing an abstracted interface to add, remove, and query FoodBrandMapping entities.
 * Serialization and deserialization are handled by JsonSerializer, leveraging JSON format for long-term data management.
 * <p>
 * Extends the functionality of the {@link BaseRepository} to accommodate the specialized requirements of FoodBrandMapping entities,
 * enforcing consistent interaction patterns and behavior across the application.
 *
 * @author Aliabbos Ashurov
 * @see BaseRepository
 * @see JsonSerializer
 * @since 04/May/2024 16:54
 */
public class FoodBrandMappingRepository implements BaseRepository<FoodBrandMapping, List<FoodBrandMapping>> {
    private static JsonSerializer<FoodBrandMapping> jsonSerializer;
    private static volatile FoodBrandMappingRepository instance;

    /**
     * Initializes the repository, setting up the jsonSerializer with the path to the
     * food-brand mappings data file and loads existing mappings from persistent storage.
     */
    private FoodBrandMappingRepository() {
    }

    public static FoodBrandMappingRepository getInstance() {
        if (instance == null) {
            synchronized (FoodBrandMappingRepository.class) {
                if (instance == null) {
                    instance = new FoodBrandMappingRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_FOOD_BRAND_MAPPING));
                }
            }
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     * Additionally, saves the updated list to the JSON storage immediately after adding.
     */
    @Override
    public boolean add(FoodBrandMapping foodBrandMapping) {
        List<FoodBrandMapping> foodBrandMappings = load();
        foodBrandMappings.add(foodBrandMapping);
        save(foodBrandMappings);
        return true;
    }

    /**
     * {@inheritDoc}
     * Once an object is removed, the changes are persisted to the JSON storage.
     */
    @Override
    public boolean remove(UUID id) {
        List<FoodBrandMapping> foodBrandMappings = load();
        boolean removed = foodBrandMappings.removeIf(foodBrandMapping -> foodBrandMapping.getId().equals(id));
        if (removed) save(foodBrandMappings);
        return removed;
    }

    /**
     * {@inheritDoc}
     * Retrieves a mapping by UUID if it exists, null otherwise.
     */
    @Override
    public FoodBrandMapping findById(UUID id) {
        List<FoodBrandMapping> foodBrandMappings = load();
        return foodBrandMappings.stream()
                .filter(foodBrandMapping -> foodBrandMapping.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<FoodBrandMapping> getAll() {
        return load();
    }

    @SneakyThrows
    @Override
    public List<FoodBrandMapping> load() {
        return jsonSerializer.read(FoodBrandMapping.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<FoodBrandMapping> foodBrandMappings) {
        jsonSerializer.write(foodBrandMappings);
    }
}
