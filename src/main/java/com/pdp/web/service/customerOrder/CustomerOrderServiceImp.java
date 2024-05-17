package com.pdp.web.service.customerOrder;

import com.pdp.web.enums.OrderStatus;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.model.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Singleton service implementation to handle operations related to CustomerOrders.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerOrderServiceImp implements CustomerOrderService {
    private static volatile CustomerOrderServiceImp instance;

    /**
     * Returns the singleton instance of CustomerOrderServiceImp.
     *
     * @return The singleton instance of CustomerOrderServiceImp.
     */
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
    public CustomerOrder getByUserID(@NonNull UUID userID) {
        return getAll().stream()
                .filter(customerOrder -> Objects.equals(customerOrder.getUserID(), userID))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all orders in process that are assigned to a specific deliverer.
     *
     * @param deliverId The UUID of the deliverer.
     * @return A list of CustomerOrder instances that are in process and assigned to the specified deliverer.
     */
    @Override
    public List<CustomerOrder> getOrdersInProcessByDeliverer(@NonNull UUID deliverId) {
        return getAll().stream()
                .filter(c -> Objects.equals(c.getDeliverID(), deliverId)
                        && (c.getOrderStatus().equals(OrderStatus.YOUR_ORDER_RECEIVED)
                        || c.getOrderStatus().equals(OrderStatus.IN_TRANSIT)))
                .toList();
    }

    /**
     * Retrieves all pending orders that are waiting for a deliverer.
     *
     * @return A list of CustomerOrder instances that are pending and waiting for a deliverer.
     */
    @Override
    public List<CustomerOrder> getPendingOrdersForDeliverer() {
        return getAll().stream()
                .filter(c -> c.getOrderStatus().equals(OrderStatus.LOOKING_FOR_A_DELIVERER))
                .toList();
    }

    /**
     * Retrieves a not confirmed order for a specific user.
     *
     * @param userID The UUID of the user.
     * @return The CustomerOrder instance that is not confirmed for the specified user, or null if not found.
     */
    @Override
    public CustomerOrder getNotConfirmedOrder(@NonNull UUID userID) {
        List<CustomerOrder> customerOrders = getAll();
        return customerOrders.stream()
                .filter(customerOrder -> customerOrder.getUserID().equals(userID) && customerOrder.getOrderStatus().equals(OrderStatus.NOT_CONFIRMED))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves or creates a not confirmed order for a specific user and branch.
     *
     * @param userId   The UUID of the user.
     * @param branchID The UUID of the branch.
     * @return The existing or newly created CustomerOrder instance that is not confirmed for the specified user and branch.
     */
    @Override
    public CustomerOrder getOrCreate(@NonNull UUID userId, @NonNull UUID branchID) {
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
     * Retrieves all archived orders for a specific user.
     *
     * @param userID The UUID of the user.
     * @return A list of CustomerOrder instances that are archived (delivered) for the specified user.
     */
    @Override
    public List<CustomerOrder> getArchive(@NonNull UUID userID) {
        return getAll().stream()
                .filter(c -> Objects.equals(c.getOrderStatus(), OrderStatus.DELIVERED)
                        && Objects.equals(c.getUserID(), userID))
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
    public List<CustomerOrder> getOrdersInProcessByUser(@NonNull UUID userId) {
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
    public boolean add(@NonNull CustomerOrder object) {
        return repository.add(object);
    }

    /**
     * Removes a customer order from the repository.
     *
     * @param id the UUID of the order to be removed
     * @return true if the order was successfully removed, otherwise false
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing customer order. Currently not implemented.
     *
     * @param customerOrder the {@link CustomerOrder} to update
     * @return false always, since the method is not implemented
     */
    @Override
    public boolean update(@NonNull CustomerOrder customerOrder) {
        List<CustomerOrder> customerOrders = getAll();
        Optional<CustomerOrder> customerOrderOptional = customerOrders.stream()
                .filter(o -> o.getId().equals(customerOrder.getId()))
                .findFirst();
        if (customerOrderOptional.isPresent()) {
            updateCustomerOrderData(customerOrderOptional.get(), customerOrder);
            repository.save(customerOrders);
            return true;
        }
        return false;
    }

    private void updateCustomerOrderData(CustomerOrder o, CustomerOrder newCustomerOrder) {
        o.setUserID(newCustomerOrder.getUserID());
        o.setBranchID(newCustomerOrder.getBranchID());
        o.setAddressID(newCustomerOrder.getAddressID());
        o.setDeliverID(newCustomerOrder.getDeliverID());
        o.setDescriptionID(newCustomerOrder.getDescriptionID());
        o.setPaymentType(newCustomerOrder.getPaymentType());
        o.setOrderPrice(newCustomerOrder.getOrderPrice());
        o.setOrderStatus(newCustomerOrder.getOrderStatus());
    }

    /**
     * Searches for customer orders based on a query string. Currently not implemented.
     *
     * @param query the query string to search for
     * @return an empty list, since the method is not implemented
     */
    @Override
    public List<CustomerOrder> search(@NonNull String query) {
        return List.of();
    }

    /**
     * Retrieves a customer order by its ID.
     *
     * @param id the UUID of the order to retrieve
     * @return the found {@link CustomerOrder}, or null if no order is found
     */
    @Override
    public CustomerOrder getByID(@NonNull UUID id) {
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
