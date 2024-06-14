package com.pdp.web.repository.category;

import com.pdp.config.SQLConfiguration;
import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.category.Category;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
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
    private final SQLHelper sql = SQLConfiguration.getSQL();

    @Override
    @SneakyThrows
    public boolean add(@NonNull Category category) {
        return sql.executeUpdate("INSERT INTO web.category(name,brand_id,image_url) VALUES(?,?,?)") > 0;
    }

    @Override
    @SneakyThrows
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.category WHERE id = ?;") > 0;
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
        return getAll().stream()
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
    @SneakyThrows
    public Set<Category> getAll() {
        Set<Category> categories = new HashSet<>();
        ResultSet rs = sql.executeQuery("SELECT * FROM web.category;");
        while (rs.next()) {
            Category category = new Category();
            category.setId(UUID.fromString(rs.getString(1)));
            category.setName(rs.getString(2));
            category.setBrandId(UUID.fromString(rs.getString(3)));
            category.setImageUrl(rs.getString(4));
            category.setCreatedAt(rs.getTimestamp(5).toLocalDateTime());
            categories.add(category);
        }
        return categories;
    }
}
