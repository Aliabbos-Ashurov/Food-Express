package com.pdp.backend.web.service.order;

import com.pdp.backend.web.model.order.Order;
import com.pdp.backend.web.repository.order.OrderRepository;
import com.pdp.backend.web.service.BaseService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Interface for services related to managing the mappings between foods and brands.
 * This service handles operations for retrieving food items associated with particular brands
 * and categories.
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
}
