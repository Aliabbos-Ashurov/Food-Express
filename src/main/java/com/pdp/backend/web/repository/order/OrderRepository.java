package com.pdp.backend.web.repository.order;

import com.pdp.backend.web.config.jsonFilePath.JsonFilePath;
import com.pdp.backend.web.model.order.Order;
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
 * Provides the data access operations for {@code Order} entities within the application, enabling
 * basic create, read, update, and delete (CRUD) capabilities as defined by the {@code BaseRepository} interface.
 * This repository utilizes {@code JsonSerializer} to serialize the {@code Order} objects to JSON and store
 * them in a file for persistent storage.
 *
 * The repository ensures all orders are kept synchronized with their persistent JSON representation,
 * allowing for a robust and recoverable data storage solution.
 *
 * @see BaseRepository
 * @see JsonSerializer
 * @see Order
 * @author Aliabbos Ashurov
 * @since 04/May/2024 16:57
 */
public class OrderRepository implements BaseRepository<Order> {
    private final JsonSerializer<Order> jsonSerializer;
    private final List<Order> orders;

    public OrderRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(PATH_ORDER));
        this.orders = load();
    }

    /**
     * Adds a new order to the repository. The order is also immediately persisted to the JSON file.
     *
     * @param order The new {@code Order} object to be added.
     * @return True if the order was successfully added to the repository; false otherwise.
     */
    @Override
    public boolean add(Order order) {

        orders.add(order);
        save();
        return true;
    }

    /**
     * Removes an order from the repository based on its ID.
     *
     * @param id The ID of the order to remove.
     * @return {@code true} if the order was found and removed; {@code false} otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        boolean removed = orders.removeIf(order -> order.getId().equals(id));
        if (removed) save();
        return removed;
    }

    /**
     * Retrieves an order from the repository based on its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The {@code Order} object corresponding to the given ID, or {@code null} if not found.
     */
    @Override
    public Order findById(UUID id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Order> getAll() {
        return Collections.unmodifiableList(orders);
    }

    /**
     * Loads orders from the JSON file using the configured serializer.
     *
     * @return A list of {@code Order} objects loaded from the JSON file.
     */
    @Override
    public List<Order> load() {
        try {
            return jsonSerializer.read(Order.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(orders);
    }
}
