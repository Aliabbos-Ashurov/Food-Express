package com.pdp.web.repository.discount;

import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.discount.Discount;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;
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
    private static JsonSerializer<Discount> jsonSerializer;
    private static volatile DiscountRepository instance;

    private DiscountRepository() {
    }

    public static DiscountRepository getInstance() {
        if (instance == null) {
            synchronized (DiscountRepository.class) {
                if (instance == null) {
                    instance = new DiscountRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(JsonFilePath.PATH_DISCOUNT));
                }
            }
        }
        return instance;
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
        List<Discount> discounts = load();
        discounts.add(discount);
        save(discounts);
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
        List<Discount> discounts = load();
        boolean removed = discounts.removeIf(discount -> discount.getId().equals(id));
        if (removed) save(discounts);
        return removed;
    }

    @Override
    public Discount findById(UUID id) {
        List<Discount> discounts = load();
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
        return load();
    }

    /**
     * Loads the list of Discounts from the JSON file into the repository.
     *
     * @return A list of Discounts, empty in case of an IOException.
     */
    @SneakyThrows
    @Override
    public List<Discount> load() {
        return jsonSerializer.read(Discount.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<Discount> discounts) {
        jsonSerializer.write(discounts);
    }
}
