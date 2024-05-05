package com.pdp.backend.web.repository.category;

import com.pdp.backend.web.config.path.ResoucePath;
import com.pdp.backend.web.model.category.Category;
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
 * This class provides the repository layer for managing {@link Category} objects, including
 * operations for adding, removing, finding by their unique identifier, and retrieving all category records.
 * <p>
 * The storage and retrieval of {@link Category} objects from a JSON source is facilitated
 * through use of a {@link JsonSerializer}. This allows categories to be persisted across application sessions.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 16:29
 */
public class CategoryRepository implements BaseRepository<Category> {
    private final JsonSerializer<Category> jsonSerializer;
    private final List<Category> categories;

    public CategoryRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(ResoucePath.CATEGORY));
        this.categories = load();
    }

    @Override
    public boolean add(Category category) {
        categories.add(category);
        save();
        return true;
    }

    @Override
    public boolean remove(UUID id) {
        boolean removed = categories.removeIf((category -> category.getId().equals(id)));
        if (removed) save();
        return removed;
    }

    /**
     * Retrieves a category from the repository based on its UUID.
     *
     * @param id The UUID of the desired category.
     * @return The {@link Category} object if found; null otherwise.
     */
    @Override
    public Category findById(UUID id) {
        return categories.stream()
                .filter((category -> category.getId().equals(id)))
                .findFirst().orElse(null);
    }

    @Override
    public List<Category> getAll() {
        return Collections.unmodifiableList(categories);
    }

    /**
     * Reads and loads the list of categories from the JSON file.
     *
     * @return A list of loaded {@link Category} objects.
     */
    @Override
    public List<Category> load() {
        try {
            return jsonSerializer.read(Category.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Persists all current categories from memory back to the JSON storage file.
     *
     * @throws IOException if an I/O error occurs during writing to the file.
     */
    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(categories);
    }
}
