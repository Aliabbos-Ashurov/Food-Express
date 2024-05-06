package com.pdp.backend.web.repository.food;

import com.pdp.backend.web.config.jsonFilePath.JsonFilePath;
import com.pdp.backend.web.model.food.Food;
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
 * Responsible for managing the persistence of Food entities. This repository defines methods
 * for adding, removing, searching, and listing all food items. It utilizes a JsonSerializer
 * to handle the conversion of Food objects to and from JSON format for disk storage.
 * <p>
 * The use of generic types allows for reusability with other entity types that implement
 * the BaseRepository interface, following consistent data access patterns and behaviors.
 * <p>
 * Extends the functionality of the BaseRepository interface to provide tailored
 * operations that fit the specific needs of managing Food entities.
 *
 * @author Aliabbos Ashurov
 * @see BaseRepository
 * @see JsonSerializer
 * @since 04/May/2024 16:51
 */
public class FoodRepository implements BaseRepository<Food,List<Food>> {
    private final JsonSerializer<Food> jsonSerializer;
    private final List<Food> foods;

    public FoodRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(PATH_FOOD));
        this.foods = load();
    }

    /**
     * Adds a new Food item to the repository and saves this addition to the JSON file.
     *
     * @param food The Food object to be added to the repository.
     * @return true if the operation was successful, false otherwise.
     */
    @Override
    public boolean add(Food food) {
        foods.add(food);
        save();
        return true;
    }

    /**
     * Removes a Food item from the repository identified by a UUID and saves the
     * changes to the JSON file.
     *
     * @param id The UUID of the Food item to remove.
     * @return true if an item with the specified ID was successfully removed, false otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        boolean removed = foods.removeIf(food -> food.getId().equals(id));
        if (removed) save();
        return removed;
    }

    /**
     * Retrieves a Food item from the repository using its UUID.
     *
     * @param id The UUID of the Food item to find.
     * @return The Food object if found, null otherwise.
     */
    @Override
    public Food findById(UUID id) {
        return foods.stream()
                .filter(food -> food.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Food> getAll() {
        return Collections.unmodifiableList(foods);
    }

    /**
     * Loads and parses the list of Food items from JSON storage into memory.
     * If the load operation fails due to an IO exception, an empty list is returned.
     *
     * @return A List populated with Food items or an empty List if an exception occurs.
     */
    @Override
    public List<Food> load() {
        try {
            return jsonSerializer.read(Food.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Persists the current list of foods to JSON storage. The @SneakyThrows annotation
     * is used to avoid the need of handling the IOException in this context.
     *
     * @throws IOException if any IO error occurs during the save operation.
     */
    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(foods);
    }
}
