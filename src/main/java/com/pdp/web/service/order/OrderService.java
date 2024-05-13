package com.pdp.web.service.order;

import com.pdp.dto.CustomOrderDTO;
import com.pdp.web.model.order.Order;
import com.pdp.web.repository.order.OrderRepository;
import com.pdp.web.service.BaseService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Interface for services related to managing the mappings between foods and brands.
 * This service handles operations for retrieving food items associated with particular brands
 * and categories.
 *
 * @author Nishonov Doniyor
 * @see Order
 * @see OrderRepository
 */
public interface OrderService extends BaseService<Order, List<Order>> {
    OrderRepository repository = OrderRepository.getInstance();

    /**
     * Calculates the total price of an order identified by its UUID.
     *
     * @param customerOrderID The unique identifier of the customer's order.
     * @return The total price of the order as a {@link BigDecimal}.
     */
    BigDecimal getOrderPrice(UUID customerOrderID);

    /**
     * Retrieves an existing order identified by its UUID, or creates a new order based on the provided {@link CustomOrderDTO}.
     *
     * @param dto   The data transfer object containing information about the custom order.
     * @param order The existing order, if available.
     * @return The retrieved or newly created {@link Order} object.
     */
    Order getOrCreate(CustomOrderDTO dto, Order order);

    /**
     * Retrieves a list of orders associated with a particular customer identified by their UUID.
     *
     * @param customerOrderID The unique identifier of the customer.
     * @return A list of orders associated with the customer.
     */
    List<Order> getOdersByCustomerID(UUID customerOrderID);
}
