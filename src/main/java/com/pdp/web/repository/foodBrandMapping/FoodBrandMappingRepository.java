package com.pdp.web.repository.foodBrandMapping;

import com.pdp.web.model.foodBrandMapping.FoodBrandMapping;
import com.pdp.web.repository.BaseRepository;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;


/**
 * Manages the persistence and retrieval of relationships between Food items and their Brands,
 * facilitating mapping operations by providing CRUD functionality for FoodBrandMapping entities.
 * Serialization and deserialization of FoodBrandMapping objects are handled by JsonSerializer,
 * using JSON format for data storage.
 * <p>
 * Extends the BaseRepository interface to ensure consistent interaction patterns and behavior
 * for managing FoodBrandMapping entities across the application.
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

    /**
     * Retrieves the singleton instance of FoodBrandMappingRepository.
     *
     * @return The singleton instance of FoodBrandMappingRepository.
     */
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
     * Adds a new FoodBrandMapping to the repository and saves the updated list to JSON storage.
     *
     * @param foodBrandMapping The FoodBrandMapping object to be added.
     * @return true if the operation was successful, false otherwise.
     */
    @Override
    public boolean add(FoodBrandMapping foodBrandMapping) {
        List<FoodBrandMapping> foodBrandMappings = load();
        foodBrandMappings.add(foodBrandMapping);
        save(foodBrandMappings);
        return true;
    }

    /**
     * Removes a FoodBrandMapping from the repository based on its UUID and persists the changes.
     *
     * @param id The UUID of the FoodBrandMapping to remove.
     * @return true if the FoodBrandMapping was successfully removed, false otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        List<FoodBrandMapping> foodBrandMappings = load();
        boolean removed = foodBrandMappings.removeIf(foodBrandMapping -> foodBrandMapping.getId().equals(id));
        if (removed) save(foodBrandMappings);
        return removed;
    }

    /**
     * Finds a FoodBrandMapping in the repository by its UUID.
     *
     * @param id The UUID of the FoodBrandMapping to find.
     * @return The FoodBrandMapping object if found, null otherwise.
     */
    @Override
    public FoodBrandMapping findById(UUID id) {
        List<FoodBrandMapping> foodBrandMappings = load();
        return foodBrandMappings.stream()
                .filter(foodBrandMapping -> foodBrandMapping.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Retrieves all FoodBrandMappings currently stored in the repository.
     *
     * @return A list of all FoodBrandMappings in the repository.
     */
    @Override
    public List<FoodBrandMapping> getAll() {
        return load();
    }

    /**
     * Loads the list of FoodBrandMappings from JSON storage into memory.
     *
     * @return A List containing FoodBrandMappings loaded from storage, or an empty List if loading fails.
     */
    @SneakyThrows
    @Override
    public List<FoodBrandMapping> load() {
        return jsonSerializer.read(FoodBrandMapping.class);
    }

    /**
     * Persists the current list of FoodBrandMappings to JSON storage.
     *
     * @param foodBrandMappings The list of FoodBrandMappings to save.
     * @throws IOException if an error occurs during the save operation.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<FoodBrandMapping> foodBrandMappings) {
        jsonSerializer.write(foodBrandMappings);
    }
}
