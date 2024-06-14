package com.pdp.web.repository.brand;

import com.pdp.config.SQLConfiguration;
import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.brand.Brand;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The {@code BrandRepository} class handles the persistence of {@link Brand} entities, offering CRUD operations
 * for managing a collection of brand records. It uses {@link JsonSerializer} to serialize and deserialize
 * {@code Brand} instances to and from a designated JSON file.
 * <p>
 * By implementing the {@link BaseRepository} interface, this class provides a consistent set of methods
 * for interacting with brand data. The repository maintains an in-memory list of {@code Brand} objects,
 * ensuring high-performance access, and synchronizes this list with a JSON storage file defined by
 * {@code PATH_BRAND}.
 * <p>
 * All operations performed on the repository are immediately persisted to the JSON storage to ensure data integrity
 * and seamless recovery on subsequent application runs.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 15:51
 */
public class BrandRepository implements BaseRepository<Brand, List<Brand>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a Brand to the repository and persists the change.
     *
     * @param brand The Brand to be added.
     * @return true if the addition is successful, false otherwise.
     */
    @Override
    @SneakyThrows
    public boolean add(@NonNull Brand brand) {
        return sql.executeUpdate("INSERT INTO web.brand(name,rating,description_id,opening_time,closing_time) VALUES(?,?,?,?,?);",
                brand.getName(), brand.getRating(), brand.getDescriptionID(), brand.getOpeningTime(), brand.getClosingTime()) > 0;
    }

    /**
     * Removes a Brand from the repository by its identifier and persists the change.
     *
     * @param id The UUID of the Brand to be removed.
     * @return true if the Brand is successfully removed, false otherwise.
     */
    @Override
    @SneakyThrows
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.brand WHERE id = ?;", id) > 0;
    }

    @Override
    public Brand findById(@NonNull UUID id) {
        return getAll().stream()
                .filter((brand -> brand.getId().equals(id)))
                .findFirst()
                .orElse(null);
    }

    @Override
    @SneakyThrows
    public List<Brand> getAll() {
        List<Brand> brands = new ArrayList<>();
        ResultSet rs = sql.executeQuery("SELECT * FROM web.brand;");
        while (rs.next()) {
            Brand brand = new Brand();
            brand.setId(UUID.fromString(rs.getString(1)));
            brand.setName(rs.getString(2));
            brand.setRating(rs.getDouble(3));
            brand.setDescriptionID(UUID.fromString(rs.getString(4)));
            brand.setOpeningTime(rs.getTimestamp(5).toLocalDateTime());
            brand.setClosingTime(rs.getTimestamp(6).toLocalDateTime());
            brand.setCreatedAt(rs.getTimestamp(7).toLocalDateTime());
            brands.add(brand);
        }
        return brands;
    }
}
