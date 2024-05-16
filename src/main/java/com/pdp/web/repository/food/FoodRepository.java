package com.pdp.web.repository.food;

import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.food.Food;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Manages persistence operations for Food entities, providing CRUD functionality
 * for adding, removing, searching, and listing food items. Utilizes JsonSerializer
 * for serialization of Food objects to JSON format and vice versa.
 * <p>
 * Implements BaseRepository interface to ensure consistent data access patterns
 * and behaviors across different entity types.
 * <p>
 * Extends BaseRepository to specialize operations for Food management.
 *
 * @author Aliabbos Ashurov
 * @see BaseRepository
 * @see JsonSerializer
 * @since 04/May/2024 16:51
 */
public class FoodRepository implements BaseRepository<Food, List<Food>> {
    private static JsonSerializer<Food> jsonSerializer;
    private static volatile FoodRepository instance;

    private FoodRepository() {
    }

    /**
     * Retrieves the singleton instance of FoodRepository.
     *
     * @return The singleton instance of FoodRepository.
     */
    public static FoodRepository getInstance() {
        if (instance == null) {
            synchronized (FoodRepository.class) {
                if (instance == null) {
                    instance = new FoodRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(JsonFilePath.PATH_FOOD));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new Food item to the repository and persists the change to JSON storage.
     *
     * @param food The Food object to be added.
     * @return true if the operation was successful, false otherwise.
     */
    @Override
    public boolean add(@NotNull Food food) {
        List<Food> foods = load();
        foods.add(food);
        save(foods);
        return true;
    }

    /**
     * Removes a Food item from the repository based on its UUID.
     *
     * @param id The UUID of the Food item to remove.
     * @return true if the item was successfully removed, false otherwise.
     */
    @Override
    public boolean remove(@NotNull UUID id) {
        List<Food> foods = load();
        boolean removed = foods.removeIf(food -> food.getId().equals(id));
        if (removed) save(foods);
        return removed;
    }

    /**
     * Finds a Food item in the repository by its UUID.
     *
     * @param id The UUID of the Food item to find.
     * @return The Food object if found, otherwise null.
     */
    @Override
    public Food findById(@NotNull UUID id) {
        List<Food> foods = load();
        return foods.stream()
                .filter(food -> food.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Retrieves all Food items currently stored in the repository.
     *
     * @return A list of all Food items in the repository.
     */
    @Override
    public List<Food> getAll() {
        return load();
    }

    /**
     * Loads the list of Food items from JSON storage into memory.
     *
     * @return A List containing Food items loaded from storage, or an empty List if loading fails.
     */
    @SneakyThrows
    @Override
    public List<Food> load() {
        return jsonSerializer.read(Food.class);
    }

    /**
     * Persists the current list of foods to JSON storage. The @SneakyThrows annotation
     * is used to avoid the need of handling the IOException in this context.
     *
     * @throws IOException if any IO error occurs during the save operation.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<Food> foods) {
        jsonSerializer.write(foods);
    }
}
