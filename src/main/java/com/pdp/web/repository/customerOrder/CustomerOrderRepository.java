package com.pdp.web.repository.customerOrder;

import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
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
public class CustomerOrderRepository implements BaseRepository<CustomerOrder, List<CustomerOrder>> {
    private static JsonSerializer<CustomerOrder> jsonSerializer;
    private static volatile CustomerOrderRepository instance;

    private CustomerOrderRepository() {
    }

    public static CustomerOrderRepository getInstance() {
        if (instance == null) {
            synchronized (CustomerOrderRepository.class) {
                if (instance == null) {
                    instance = new CustomerOrderRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(JsonFilePath.PATH_CUSTOMER_ORDER));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a CustomerOrder to the repository and persistently writes it to the JSON file.
     *
     * @param customerOrder The CustomerOrder to store.
     * @return True if the addition is successful, indicating the customerOrder
     * was added and the list was saved.
     */
    @Override
    public boolean add(@NonNull CustomerOrder customerOrder) {
        List<CustomerOrder> customerOrders = load();
        customerOrders.add(customerOrder);
        save(customerOrders);
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
    public boolean remove(@NonNull UUID id) {
        List<CustomerOrder> customerOrders = load();
        boolean removed = customerOrders.removeIf(customerOrder -> customerOrder.getId().equals(id));
        if (removed) save(customerOrders);
        return removed;
    }

    @Override
    public CustomerOrder findById(@NonNull UUID id) {
        List<CustomerOrder> customerOrders = load();
        return customerOrders.stream()
                .filter(customerOrder -> customerOrder.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<CustomerOrder> getAll() {
        return load();
    }

    @SneakyThrows
    @Override
    public List<CustomerOrder> load() {
        return jsonSerializer.read(CustomerOrder.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<CustomerOrder> customerOrders) {
        jsonSerializer.write(customerOrders);
    }
}
