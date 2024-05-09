package com.pdp.backend.web.service.order;

import com.pdp.backend.web.model.order.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Singleton service class for managing orders.
 * Implements thread-safe lazy initialization with double-checked locking.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderServiceImp implements OrderService {
    private static volatile OrderServiceImp instance;

    public static OrderServiceImp getInstance() {
        if (instance == null) {
            synchronized (OrderServiceImp.class) {
                if (instance == null) {
                    instance = new OrderServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Retrieves the price of a specific order by the customer order ID.
     *
     * @param customerOrderID the UUID of the customer order to retrieve the price for
     * @return the price of the order as {@link BigDecimal} or null if the order does not exist
     */
    @Override
    public BigDecimal getOrderPrice(UUID customerOrderID) {
        return getAll().stream()
                .filter(c -> Objects.equals(c.getCustomerOrderID(), customerOrderID))
                .map(Order::getFoodPrice)
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a new order to the repository.
     *
     * @param order the {@link Order} object to be added
     * @return true if the order was successfully added, false otherwise
     */
    @Override
    public boolean add(Order order) {
        return repository.add(order);
    }

    /**
     * Removes an order from the repository using its UUID.
     *
     * @param id the UUID of the order to be removed
     * @return true if the order was successfully removed, false otherwise
     */
    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing order in the repository. Currently not implemented.
     *
     * @param order the {@link Order} object to update
     * @return false, indicating the operation is not supported yet
     */
    @Override
    public boolean update(Order order) {
        return false;
    }

    /**
     * Searches for orders based on a query string. Currently not implemented.
     *
     * @param query the search query string
     * @return an empty list, as the method is not implemented
     */
    @Override
    public List<Order> search(String query) {
        return List.of();
    }

    /**
     * Retrieves an order by its UUID.
     *
     * @param id the UUID of the order to retrieve
     * @return the {@link Order} object, or null if no order is found
     */
    @Override
    public Order getByID(UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all orders from the repository.
     *
     * @return a list of all {@link Order} objects
     */
    @Override
    public List<Order> getAll() {
        return repository.getAll();
    }
}
