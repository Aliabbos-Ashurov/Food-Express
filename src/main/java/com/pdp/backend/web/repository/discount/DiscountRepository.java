package com.pdp.backend.web.repository.discount;

import com.pdp.backend.web.model.discount.Discount;
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
 * Manages the persistence and retrieval of Discount entities, providing CRUD
 * operations such as adding, removing, finding by ID, and listing all discounts.
 * Serialization and deserialization of Discount objects are performed by a JsonSerializer,
 * ensuring the data is maintained in a JSON file as defined in JsonFilePath.
 * <p>
 * Implements the BaseRepository interface, which includes methods guaranteeing
 * consistent access to the underlying Discount data.
 *
 * @author Aliabbos Ashurov
 * @see BaseRepository
 * @since 04/May/2024 16:47
 */
public class DiscountRepository implements BaseRepository<Discount,List<Discount>> {
    private final JsonSerializer<Discount> jsonSerializer;
    private final List<Discount> discounts;

    public DiscountRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(PATH_DISCOUNT));
        this.discounts = load();
    }

    /**
     * Adds a new Discount to the repository, automatically saving the updated
     * list to the designated JSON file.
     *
     * @param discount The Discount to be added.
     * @return True if the operation is successful, false otherwise.
     */
    @Override
    public boolean add(Discount discount) {
        discounts.add(discount);
        save();
        return true;
    }

    /**
     * Removes a Discount from the repository based on its UUID and updates
     * the persistent store accordingly.
     *
     * @param id The UUID of the Discount to remove.
     * @return True if the Discount is successfully removed, false otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        boolean removed = discounts.removeIf(discount -> discount.getId().equals(id));
        if (removed) save();
        return removed;
    }

    @Override
    public Discount findById(UUID id) {
        return discounts.stream()
                .filter(discount -> discount.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Provides an unmodifiable view of all Discount entities currently available
     * in the repository without sacrificing data integrity.
     *
     * @return An unmodifiable List of Discounts.
     */
    @Override
    public List<Discount> getAll() {
        return discounts;
    }

    /**
     * Loads the list of Discounts from the JSON file into the repository.
     *
     * @return A list of Discounts, empty in case of an IOException.
     */
    @Override
    public List<Discount> load() {
        try {
            return jsonSerializer.read(Discount.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(discounts);
    }
}
