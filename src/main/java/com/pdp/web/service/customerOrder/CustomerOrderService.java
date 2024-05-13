package com.pdp.web.service.customerOrder;

import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.repository.customerOrder.CustomerOrderRepository;
import com.pdp.web.service.BaseService;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Defines the business logic for managing customer orders.
 * Extends the generic BaseService interface to provide CRUD operations for CustomerOrder
 * along with additional methods to retrieve specific subsets of orders.
 *
 * @see BaseService
 * @see CustomerOrder
 */
public interface CustomerOrderService extends BaseService<CustomerOrder, List<CustomerOrder>> {
    CustomerOrderRepository repository = CustomerOrderRepository.getInstance();

    /**
     * Retrieves a list of archived orders for a specific user.
     *
     * @param userID The unique identifier of the user.
     * @return A list of archived {@link CustomerOrder} associated with the user.
     */
    List<CustomerOrder> getArchive(@NonNull UUID userID);

    /**
     * Retrieves a list of orders that are currently in process for a specific user.
     *
     * @param userId The unique identifier of the user whose active orders are to be retrieved.
     * @return A list of in-process {@link CustomerOrder} associated with the user.
     */
    List<CustomerOrder> getOrdersInProcessByUser(@NonNull UUID userId);

    /**
     * Retrieves or creates a customer order for a specific user and branch.
     *
     * @param userId   The unique identifier of the user.
     * @param branchID The unique identifier of the branch.
     * @return The existing or newly created {@link CustomerOrder} associated with the user and branch.
     */
    CustomerOrder getOrCreate(@NonNull UUID userId, UUID branchID);

    /**
     * Retrieves a not confirmed customer order for a specific user.
     *
     * @param userID The unique identifier of the user.
     * @return The not confirmed {@link CustomerOrder} associated with the user.
     */
    CustomerOrder getNotConfirmedOrder(@NonNull UUID userID);

    /**
     * Retrieves a list of orders that are currently in process for a specific deliverer.
     *
     * @param deliverId The unique identifier of the deliverer.
     * @return A list of in-process {@link CustomerOrder} associated with the deliverer.
     */
    List<CustomerOrder> getOrdersInProcessByDeliverer(@NonNull UUID deliverId);

    /**
     * Retrieves a list of pending orders for a deliverer.
     *
     * @return A list of pending {@link CustomerOrder} that require a deliverer.
     */
    List<CustomerOrder> getPendingOrdersForDeliverer();
}
