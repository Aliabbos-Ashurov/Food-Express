package com.pdp.backend.web.service.order;

import com.pdp.backend.web.model.order.Order;
import com.pdp.backend.web.repository.order.OrderRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

public interface OrderService extends BaseService<Order, List<Order>> {
    OrderRepository repository = new OrderRepository();
}
