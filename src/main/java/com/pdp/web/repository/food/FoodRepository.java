package com.pdp.web.repository.food;

import com.pdp.config.SQLConfiguration;
import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.food.Food;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import sql.helper.SQLHelper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
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
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a new Food item to the repository and persists the change to JSON storage.
     *
     * @param food The Food object to be added.
     * @return true if the operation was successful, false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean add(@NotNull Food food) {
        return sql.executeUpdate("INSERT INTO web.food(name,image_url,description_id,price,category_id) VALUES (?,?,?,?,?);",
                food.getName(), food.getImageUrl(), food.getDescriptionID(), food.getPrice(), food.getCategoryID()) > 0;
    }

    /**
     * Removes a Food item from the repository based on its UUID.
     *
     * @param id The UUID of the Food item to remove.
     * @return true if the item was successfully removed, false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean remove(@NotNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.food WHERE id = ?;", id) > 0;
    }

    @Override
    @SneakyThrows
    public boolean update(@NonNull Food food) {
        return sql.executeUpdate("UPDATE web.food set name = ?,image_url=?,description_id=?,price=?,category_id=? WHERE id = ?;",
                food.getName(), food.getImageUrl(), food.getDescriptionID(), food.getPrice(), food.getCategoryID(), food.getId()) > 0;
    }

    /**
     * Finds a Food item in the repository by its UUID.
     *
     * @param id The UUID of the Food item to find.
     * @return The Food object if found, otherwise null.
     */
    @Override
    public Food findById(@NotNull UUID id) {
        return getAll().stream().filter(f -> f.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Retrieves all Food items currently stored in the repository.
     *
     * @return A list of all Food items in the repository.
     */
    @SneakyThrows
    @Override
    public List<Food> getAll() {
        ResultSet resultSet = sql.executeQuery("SELECT * FROM web.food;");
        List<Food> foods = new ArrayList<>();
        while (resultSet.next()) {
            Food food = new Food();
            food.setId(UUID.fromString(resultSet.getString("id")));
            food.setName(resultSet.getString("name"));
            food.setPrice(BigDecimal.valueOf(resultSet.getDouble("price")));
            food.setDescriptionID(UUID.fromString(resultSet.getString("description_id")));
            food.setCategoryID(UUID.fromString(resultSet.getString("category_id")));
            food.setImageUrl(resultSet.getString("image_url"));
            food.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            foods.add(food);
        }
        return foods;
    }
}
