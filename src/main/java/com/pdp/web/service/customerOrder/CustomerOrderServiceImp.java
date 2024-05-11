package com.pdp.web.service.customerOrder;

import com.pdp.web.enums.OrderStatus;
import com.pdp.web.model.customerOrder.CustomerOrder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Singleton service implementation to handle operations related to CustomerOrders.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerOrderServiceImp implements CustomerOrderService {
    private static volatile CustomerOrderServiceImp instance;

    public static CustomerOrderServiceImp getInstance() {
        if (instance == null) {
            synchronized (CustomerOrderServiceImp.class) {
                if (instance == null) {
                    instance = new CustomerOrderServiceImp();
                }
            }
        }
        return instance;
    }

    @Override
    public CustomerOrder getNotConfirmedOrder(UUID userID) {
        List<CustomerOrder> customerOrders = getAll();
        return customerOrders.stream()
                .filter(customerOrder -> customerOrder.getUserID().equals(userID) && customerOrder.getOrderStatus().equals(OrderStatus.NOT_CONFIRMED))
                .findFirst()
                .orElse(null);
    }

    @Override
    public CustomerOrder getOrCreate(UUID userId,UUID branchID) {
        return getAll().stream()
                .filter(customerOrder -> customerOrder.getUserID().equals(userId)
                        && customerOrder.getOrderStatus().equals(OrderStatus.NOT_CONFIRMED))
                .findFirst().orElseGet(() -> {
                    CustomerOrder build = CustomerOrder.builder()
                            .userID(userId)
                            .orderStatus(OrderStatus.NOT_CONFIRMED)
                            .branchID(branchID)
                            .build();
                    add(build);
                    return build;
                });
    }

    /**
     * Fetches a list of all delivered orders archived in the system.
     *
     * @param userID the UUID of the user to fetch archived orders for
     * @return a list of {@link CustomerOrder} that are marked as delivered
     */
    @Override
    public List<CustomerOrder> getArchive(UUID userID) {
        return getAll().stream()
                .filter(c -> Objects.equals(c.getOrderStatus(), OrderStatus.DELIVERED))
                .toList();
    }

    /**
     * Retrieves all orders for a specific user that are currently in process.
     * In process statuses include: LOOKING_FOR_A_DELIVERER, IN_TRANSIT,
     * YOUR_ORDER_RECEIVED, and PROCESSING.
     *
     * @param userId the UUID of the user whose in-process orders are to be fetched
     * @return a list of {@link CustomerOrder} currently in process for the specified user
     */
    @Override
    public List<CustomerOrder> getOrdersInProcessByUser(UUID userId) {
        return getAll().stream()
                .filter(c -> Objects.equals(c.getOrderStatus(), OrderStatus.LOOKING_FOR_A_DELIVERER)
                        || Objects.equals(c.getOrderStatus(), OrderStatus.IN_TRANSIT)
                        || Objects.equals(c.getOrderStatus(), OrderStatus.YOUR_ORDER_RECEIVED)
                        || Objects.equals(c.getOrderStatus(), OrderStatus.PROCESSING))
                .toList();
    }

    /**
     * Adds a new customer order to the repository.
     *
     * @param object the {@link CustomerOrder} to add
     * @return true if the order was successfully added, otherwise false
     */
    @Override
    public boolean add(CustomerOrder object) {
        return repository.add(object);
    }

    /**
     * Removes a customer order from the repository.
     *
     * @param id the UUID of the order to be removed
     * @return true if the order was successfully removed, otherwise false
     */
    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing customer order. Currently not implemented.
     *
     * @param customerOrder the {@link CustomerOrder} to update
     * @return false always, since the method is not implemented
     */
    @Override
    public boolean update(CustomerOrder customerOrder) {
        return false;
    }

    /**
     * Searches for customer orders based on a query string. Currently not implemented.
     *
     * @param query the query string to search for
     * @return an empty list, since the method is not implemented
     */
    @Override
    public List<CustomerOrder> search(String query) {
        return List.of();
    }

    /**
     * Retrieves a customer order by its ID.
     *
     * @param id the UUID of the order to retrieve
     * @return the found {@link CustomerOrder}, or null if no order is found
     */
    @Override
    public CustomerOrder getByID(UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all customer orders from the repository.
     *
     * @return a list of all {@link CustomerOrder}
     */
    @Override
    public List<CustomerOrder> getAll() {
        return repository.getAll();
    }
}
