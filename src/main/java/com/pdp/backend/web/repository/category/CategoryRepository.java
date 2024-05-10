package com.pdp.backend.web.repository.category;

import com.pdp.backend.web.model.category.Category;
import com.pdp.backend.web.repository.BaseRepository;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

/**
 * This class provides the repository layer for managing {@link Category} objects, including
 * operations for adding, removing, finding by their unique identifier, and retrieving all category records.
 * <p>
 * The storage and retrieval of {@link Category} objects from a JSON source is facilitated
 * through use of a {@link JsonSerializer}. This allows categories to be persisted across application sessions.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 16:29
 */
public class CategoryRepository implements BaseRepository<Category, Set<Category>> {
    private static volatile CategoryRepository instance;
    private static JsonSerializer<Category> jsonSerializer;

    private CategoryRepository() {
    }

    public static CategoryRepository getInstance() {
        if (instance == null) {
            synchronized (CategoryRepository.class) {
                if (instance == null) {
                    instance = new CategoryRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_CATEGORY));
                }
            }
        }
        return instance;
    }

    @Override
    public boolean add(@NonNull Category category) {
        Set<Category> categories = load();
        boolean removed = categories.add(category);
        if (removed) save(categories);
        return removed;
    }

    @Override
    public boolean remove(@NonNull UUID id) {
        Set<Category> categories = load();
        boolean removed = categories.removeIf((category -> category.getId().equals(id)));
        if (removed) save(categories);
        return removed;
    }

    /**
     * Retrieves a category from the repository based on its UUID.
     *
     * @param id The UUID of the desired category.
     * @return The {@link Category} object if found; null otherwise.
     */
    @Override
    public Category findById(@NonNull UUID id) {
        Set<Category> categories = load();
        return categories.stream()
                .filter((category -> category.getId().equals(id)))
                .findFirst()
                .orElse(null);
    }


    @Override
    public Set<Category> getAll() {
        return load();
    }

    /**
     * Reads and loads the list of categories from the JSON file.
     *
     * @return A list of loaded {@link Category} objects.
     */
    @SneakyThrows
    @Override
    public Set<Category> load() {
        List<Category> list = jsonSerializer.read(Category.class);
        return new HashSet<>(list);
    }

    /**
     * Persists all current categories from memory back to the JSON storage file.
     *
     * @throws IOException if an I/O error occurs during writing to the file.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull Set<Category> categories) {
        jsonSerializer.write(new ArrayList<>(categories));
    }
}
