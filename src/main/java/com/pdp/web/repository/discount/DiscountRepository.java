package com.pdp.web.repository.discount;

import com.pdp.config.SQLConfiguration;
import com.pdp.web.model.discount.Discount;
import com.pdp.web.repository.BaseRepository;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Manages the persistence and retrieval of Discount entities, providing CRUD
 * operations such as adding, removing, finding by ID, and listing all discounts.
 * Serialization and deserialization of Discount objects are performed by a JsonSerializer,
 * ensuring the data is maintained in a JSON file as defined in JsonFilePath.
 * <p>
 * Implements the {@link BaseRepository} interface, which includes methods guaranteeing
 * consistent access to the underlying Discount data.
 *
 * @author Aliabbos Ashurov
 * @see BaseRepository
 * @since 04/May/2024 16:47
 */
public class DiscountRepository implements BaseRepository<Discount, List<Discount>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a new Discount to the repository, automatically saving the updated
     * list to the designated JSON file.
     *
     * @param discount The Discount to be added.
     * @return True if the operation is successful, false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean add(@NotNull Discount discount) {
        return (sql.executeUpdate("INSERT INTO web.discount(food_id,starting_time,ending_time,description_id,discount_percentage) VALUES (?,?,?,?,?);",
                discount.getFoodID(), discount.getStartingTime(), discount.getEndingTime(), discount.getDescriptionID(), discount.getDiscountPersentage())) > 0;
    }

    /**
     * Removes a Discount from the repository based on its UUID and updates
     * the persistent store accordingly.
     *
     * @param id The UUID of the Discount to remove.
     * @return True if the Discount is successfully removed, false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean remove(@NotNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.discount WHERE id = ?;", id) > 0;
    }

    @Override
    public Discount findById(@NotNull UUID id) {
        return getAll().stream().filter(discount -> Objects.equals(discount.getId(), id)).findFirst().orElse(null);
    }

    /**
     * Provides an unmodifiable view of all Discount entities currently available
     * in the repository without sacrificing data integrity.
     *
     * @return An unmodifiable List of Discounts.
     */
    @SneakyThrows
    @Override
    public List<Discount> getAll() {
        ResultSet resultSet = sql.executeQuery("SELECT * FROM web.discount;");
        List<Discount> discounts = new ArrayList<>();
        while (resultSet.next()) {
            Discount discount = new Discount();
            discount.setId(UUID.fromString(resultSet.getString("id")));
            discount.setFoodID(UUID.fromString(resultSet.getString("food_id")));
            discount.setDescriptionID(UUID.fromString(resultSet.getString("description_id")));
            discount.setDiscountPersentage(resultSet.getInt("discount_percentage"));
            discount.setStartingTime(resultSet.getTimestamp("starting_time").toLocalDateTime());
            discount.setEndingTime(resultSet.getTimestamp("ending_time").toLocalDateTime());
            discount.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            discounts.add(discount);
        }
        return discounts;
    }
}
