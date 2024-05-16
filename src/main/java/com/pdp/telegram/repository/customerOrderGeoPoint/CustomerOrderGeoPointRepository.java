package com.pdp.telegram.repository.customerOrderGeoPoint;


import com.pdp.telegram.model.customerOrderGeoPoint.CustomerOrderGeoPoint;
import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.repository.BaseRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Repository class for managing customer order geo points.
 * Handles data storage and retrieval.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 14:37
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerOrderGeoPointRepository implements BaseRepository<CustomerOrderGeoPoint, List<CustomerOrderGeoPoint>> {
    private static volatile CustomerOrderGeoPointRepository instance;
    private static JsonSerializer<CustomerOrderGeoPoint> jsonSerializer;

    /**
     * Returns the singleton instance of the repository.
     *
     * @return The singleton instance of the repository.
     */
    public static CustomerOrderGeoPointRepository getInstance() {
        if (instance == null) {
            synchronized (CustomerOrderGeoPointRepository.class) {
                if (instance == null) {
                    instance = new CustomerOrderGeoPointRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_TELEGRAM_CUSTOMER_ORDER_GEO_POINT));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a customer order geo point to the repository.
     *
     * @param object The customer order geo point to add.
     * @return True if the addition was successful, false otherwise.
     */
    @Override
    public boolean add(@NonNull CustomerOrderGeoPoint object) {
        List<CustomerOrderGeoPoint> load = load();
        load.add(object);
        save(load);
        return true;
    }

    /**
     * Removes a customer order geo point from the repository.
     *
     * @param id The ID of the customer order geo point to remove.
     * @return True if the removal was successful, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<CustomerOrderGeoPoint> load = load();
        boolean b = load.removeIf(c -> Objects.equals(c.getId(), id));
        if (b) save(load);
        return b;
    }

    /**
     * Finds a customer order geo point by its ID.
     *
     * @param id The ID of the customer order geo point to find.
     * @return The customer order geo point with the specified ID, or null if not found.
     */
    @Override
    public CustomerOrderGeoPoint findById(@NonNull UUID id) {
        return load().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all customer order geo points from the repository.
     *
     * @return A list of all customer order geo points.
     */
    @Override
    public List<CustomerOrderGeoPoint> getAll() {
        return load();
    }

    /**
     * Loads customer order geo points from storage.
     *
     * @return A list of loaded customer order geo points.
     * @throws Exception If there's an error during deserialization.
     */
    @SneakyThrows
    @Override
    public List<CustomerOrderGeoPoint> load() {
        return jsonSerializer.read(CustomerOrderGeoPoint.class);
    }

    /**
     * Saves customer order geo points to storage.
     *
     * @param customerOrderGeoPoints The list of customer order geo points to save.
     * @throws Exception If there's an error during serialization.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<CustomerOrderGeoPoint> customerOrderGeoPoints) {
        jsonSerializer.write(customerOrderGeoPoints);
    }
}
