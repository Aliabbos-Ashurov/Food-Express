package com.pdp.web.repository.category;

import com.pdp.web.model.category.Category;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;


/**
 * Provides the repository infrastructure for managing Category entities.
 * Offers standard CRUD operations including addition, removal, search by ID,
 * and acquisition of all categories.
 * <p>
 * Utilizes the JsonSerializer to facilitate the serialization and deserialization
 * processes for Category objects, thereby ensuring data persistence across application sessions.
 *
 * @author Aliabbos Ashurov
 * @see JsonSerializer
 * @see BaseRepository
 * @see UUID
 * @see Category
 * @see Set
 * @since 04/May/2024 16:29
 */
public class CategoryRepository implements BaseRepository<Category, Set<Category>> {
    private static volatile CategoryRepository instance;
    private static JsonSerializer<Category> jsonSerializer;

    private CategoryRepository() {
    }

    /**
     * Retrieves a singleton instance of {@code CategoryRepository}. On initial access,
     * synchronizes access to ensure only one instance is created, thereafter returning
     * said instance for future calls.
     *
     * @return A singleton {@code CategoryRepository} instance.
     * @see #instance
     * @see JsonSerializer
     * @see JsonFilePath#PATH_CATEGORY
     */
    public static CategoryRepository getInstance() {
        if (instance == null) {
            synchronized (CategoryRepository.class) {
                if (instance == null) {
                    instance = new CategoryRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(JsonFilePath.PATH_CATEGORY));
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
     * Retrieves a {@code Category} object by its unique identifier, returning
     * an {@code Optional}. If the category does not exist, an empty Optional is returned.
     *
     * @param id The unique identifier of the category to search.
     * @return An {@code Optional<Category>} with the category found, or empty if not found.
     */
    @Override
    public Category findById(@NonNull UUID id) {
        Set<Category> categories = load();
        return categories.stream()
                .filter((category -> category.getId().equals(id)))
                .findFirst()
                .orElse(null);
    }


    /**
     * Retrieves the entire set of categories currently managed by the repository.
     *
     * @return A {@code Set} containing all categories.
     */
    @Override
    public Set<Category> getAll() {
        return load();
    }

    /**
     * Reads and deserializes the JSON file into a {@code Set} containing {@code Category} objects.
     * Uses the {@link JsonSerializer} utility for deserialization.
     *
     * @return A {@code Set} containing deserialized {@code Category} objects.
     * @throws IOException If an error occurs during deserialization.
     */
    @SneakyThrows
    @Override
    public Set<Category> load() {
        List<Category> list = jsonSerializer.read(Category.class);
        return new HashSet<>(list);
    }

    /**
     * Serializes and persists all categories from the in-memory {@code Set} to the JSON
     * storage file. Uses the {@link JsonSerializer} utility for serialization.
     *
     * @param categories The {@code Set} of {@code Category} objects to persist.
     * @throws IOException If an error occurs during serialization.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull Set<Category> categories) {
        jsonSerializer.write(new ArrayList<>(categories));
    }
}
