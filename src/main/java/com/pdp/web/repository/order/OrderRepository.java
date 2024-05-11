package com.pdp.web.repository.order;

import com.pdp.web.model.order.Order;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Provides the data access operations for {@code Order} entities within the application, enabling
 * basic create, read, update, and delete (CRUD) capabilities as defined by the {@code BaseRepository} interface.
 * This repository utilizes {@code JsonSerializer} to serialize the {@code Order} objects to JSON and store
 * them in a file for persistent storage.
 * <p>
 * The repository ensures all orders are kept synchronized with their persistent JSON representation,
 * allowing for a robust and recoverable data storage solution.
 *
 * @author Aliabbos Ashurov
 * @see BaseRepository
 * @see JsonSerializer
 * @see Order
 * @since 04/May/2024 16:57
 */
public class OrderRepository implements BaseRepository<Order, List<Order>> {
    private static JsonSerializer<Order> jsonSerializer;
    private static volatile OrderRepository instance;

    private OrderRepository() {
    }

    public static OrderRepository getInstance() {
        if (instance == null) {
            synchronized (OrderRepository.class) {
                if (instance == null) {
                    instance = new OrderRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(JsonFilePath.PATH_ORDER));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new order to the repository. The order is also immediately persisted to the JSON file.
     *
     * @param order The new {@code Order} object to be added.
     * @return True if the order was successfully added to the repository; false otherwise.
     */
    @Override
    public boolean add(@NonNull Order order) {
        List<Order> orders = load();
        orders.add(order);
        save(orders);
        return true;
    }

    /**
     * Removes an order from the repository based on its ID.
     *
     * @param id The ID of the order to remove.
     * @return {@code true} if the order was found and removed; {@code false} otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<Order> orders = load();
        boolean removed = orders.removeIf(order -> order.getId().equals(id));
        if (removed) save(orders);
        return removed;
    }

    /**
     * Retrieves an order from the repository based on its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The {@code Order} object corresponding to the given ID, or {@code null} if not found.
     */
    @Override
    public Order findById(@NonNull UUID id) {
        List<Order> orders = load();
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Order> getAll() {
        return load();
    }

    /**
     * Loads orders from the JSON file using the configured serializer.
     *
     * @return A list of {@code Order} objects loaded from the JSON file.
     */
    @SneakyThrows
    @Override
    public List<Order> load() {
        return jsonSerializer.read(Order.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<Order> orders) {
        jsonSerializer.write(orders);
    }
}
