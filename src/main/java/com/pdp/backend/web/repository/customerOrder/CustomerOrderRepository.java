package com.pdp.backend.web.repository.customerOrder;

import com.pdp.backend.web.model.customerOrder.CustomerOrder;
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
 * Provides CRUD operations for CustomerOrder entities, encapsulating the logic required
 * to add, remove, retrieve, and list all customer orders persisted in the system.
 * <p>
 * Leverages JsonSerializer for translating CustomerOrder objects to and from JSON, supporting
 * data persistence to a file defined by JsonFilePath.CUSTOMER_ORDER.
 * <p>
 * Suspends normal checks during exception handling, allowing unchecked exceptions to propagate,
 * as indicated by the use of @SneakyThrows. Error handling is targeted towards console logging
 * and returning alternative results when exception conditions are encountered.
 *
 * @author Aliabbos Ashurov
 * @see BaseRepository
 * @since 04/May/2024 16:37
 */
public class CustomerOrderRepository implements BaseRepository<CustomerOrder,List<CustomerOrder>> {
    private final JsonSerializer<CustomerOrder> jsonSerializer;
    private final List<CustomerOrder> customerOrders;

    public CustomerOrderRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(PATH_CUSTOMER_ORDER));
        this.customerOrders = load();
    }

    /**
     * Adds a CustomerOrder to the repository and persistently writes it to the JSON file.
     *
     * @param customerOrder The CustomerOrder to store.
     * @return True if the addition is successful, indicating the customerOrder
     * was added and the list was saved.
     */
    @Override
    public boolean add(CustomerOrder customerOrder) {
        customerOrders.add(customerOrder);
        save();
        return true;
    }

    /**
     * Removes a CustomerOrder based on its UUID from the repository and writes
     * the updated list persistently to the JSON file.
     *
     * @param id The UUID of the customerOrder to remove.
     * @return True if the customerOrder was successfully found and removed, false otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        boolean removed = customerOrders.removeIf(customerOrder -> customerOrder.getId().equals(id));
        if (removed) save();
        return removed;
    }

    @Override
    public CustomerOrder findById(UUID id) {
        return customerOrders.stream()
                .filter(customerOrder -> customerOrder.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<CustomerOrder> getAll() {
        return Collections.unmodifiableList(customerOrders);
    }

    @Override
    public List<CustomerOrder> load() {
        try {
            return jsonSerializer.read(CustomerOrder.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(customerOrders);
    }
}
