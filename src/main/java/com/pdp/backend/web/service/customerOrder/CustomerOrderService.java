package com.pdp.backend.web.service.customerOrder;

import com.pdp.backend.web.model.customerOrder.CustomerOrder;
import com.pdp.backend.web.repository.customerOrder.CustomerOrderRepository;
import com.pdp.backend.web.service.BaseService;

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
    CustomerOrderRepository repository = new CustomerOrderRepository();

    /**
     * Retrieves a list of archived orders for a specific user.
     *
     * @param userID The unique identifier of the user.
     * @return A list of archived {@link CustomerOrder} associated with the user.
     */
    List<CustomerOrder> getArchive(UUID userID);

    /**
     * Retrieves a list of orders that are currently in process for a specific user.
     *
     * @param userId The unique identifier of the user whose active orders are to be retrieved.
     * @return A list of in-process {@link CustomerOrder} associated with the user.
     */
    List<CustomerOrder> getOrdersInProcessByUser(UUID userId);

}
