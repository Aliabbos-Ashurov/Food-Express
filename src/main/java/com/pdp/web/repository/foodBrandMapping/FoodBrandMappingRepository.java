package com.pdp.web.repository.foodBrandMapping;

import com.pdp.config.SQLConfiguration;
import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.foodBrandMapping.FoodBrandMapping;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a new FoodBrandMapping to the repository and saves the updated list to JSON storage.
     *
     * @param foodBrandMapping The FoodBrandMapping object to be added.
     * @return true if the operation was successful, false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean add(@NotNull FoodBrandMapping foodBrandMapping) {
        return Objects.nonNull(sql.executeQuery("INSERT INTO web.food_brand_mapping(category_name varchar,food_id,brand_id) VALUES (?,?,?);"
                , foodBrandMapping.getCategoryName(), foodBrandMapping.getFoodID(), foodBrandMapping.getBrandID()));
    }

    /**
     * Removes a FoodBrandMapping from the repository based on its UUID and persists the changes.
     *
     * @param id The UUID of the FoodBrandMapping to remove.
     * @return true if the FoodBrandMapping was successfully removed, false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean remove(@NotNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.food_brand_mapping WHERE id = ?;", id) > 0;
    }

    @Override
    @SneakyThrows
    public boolean update(@NonNull FoodBrandMapping foodBrandMapping) {
        return sql.executeUpdate("UPDATE web.food_brand_mapping set category_name = ?,food_id=?,brand_id WHERE id = ?;",
                foodBrandMapping.getCategoryName(), foodBrandMapping.getFoodID(), foodBrandMapping.getBrandID(), foodBrandMapping.getId()) > 0;
    }

    /**
     * Finds a FoodBrandMapping in the repository by its UUID.
     *
     * @param id The UUID of the FoodBrandMapping to find.
     * @return The FoodBrandMapping object if found, null otherwise.
     */
    @Override
    public FoodBrandMapping findById(@NotNull UUID id) {
        return getAll().stream().filter(foodBrandMapping -> foodBrandMapping.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Retrieves all FoodBrandMappings currently stored in the repository.
     *
     * @return A list of all FoodBrandMappings in the repository.
     */
    @SneakyThrows
    @Override
    public List<FoodBrandMapping> getAll() {
        ResultSet resultSet = sql.executeQuery("SELECT * FROM web.food_brand_mapping;");
        List<FoodBrandMapping> foodBrandMappings = new ArrayList<>();
        while (resultSet.next()) {
            FoodBrandMapping foodBrandMapping = new FoodBrandMapping();
            foodBrandMapping.setId(UUID.fromString(resultSet.getString("id")));
            foodBrandMapping.setFoodID(UUID.fromString(resultSet.getString("food_id")));
            foodBrandMapping.setBrandID(UUID.fromString(resultSet.getString("brand_id")));
            foodBrandMapping.setCategoryName(resultSet.getString("category_name"));
            foodBrandMapping.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            foodBrandMappings.add(foodBrandMapping);
        }
        return foodBrandMappings;
    }
}
