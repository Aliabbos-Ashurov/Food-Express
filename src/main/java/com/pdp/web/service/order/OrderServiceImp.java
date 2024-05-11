package com.pdp.web.service.order;

import com.pdp.config.ThreadSafeBeansContainer;
import com.pdp.dto.CustomOrderDTO;
import com.pdp.web.enums.OrderStatus;
import com.pdp.web.model.branch.Branch;
import com.pdp.web.model.customerOrder.CustomerOrder;
import com.pdp.web.model.food.Food;
import com.pdp.web.model.order.Order;
import com.pdp.web.service.branch.BranchService;
import com.pdp.web.service.customerOrder.CustomerOrderService;
import com.pdp.web.service.food.FoodService;
import com.pdp.web.service.foodBrandMapping.FoodBrandMappingService;
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
    CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();
    FoodBrandMappingService foodBrandMappingService = ThreadSafeBeansContainer.foodBrandMappingServiceThreadLocal.get();
    BranchService branchService = ThreadSafeBeansContainer.branchServiceThreadLocal.get();
    FoodService foodService = ThreadSafeBeansContainer.foodServiceThreadLocal.get();
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

    @Override
    public Order getOrCreate(CustomOrderDTO dto, Order order) {
        CustomerOrder exists = findExistingOrder(dto);
        if (Objects.nonNull(exists)) {
            boolean foodFromCurrentBrand = isFoodFromCurrentBrand(order, exists.getBranchID());
            if (foodFromCurrentBrand) {
                Order orderWithSameFood = findOrderWithSameFood(order);
                if (Objects.nonNull(orderWithSameFood)) updateOrderQuantityAndPrice(orderWithSameFood, order);
                else return createNewOrder(order, exists);

            } else {
                customerOrderService.remove(exists.getId());
                CustomerOrder build = CustomerOrder.builder()
                        .userID(dto.userID())
                        .branchID(dto.branchID())
                        .build();
                customerOrderService.add(build);
                order.setCustomerOrderID(build.getId());
                add(order);
                removeAllOrdersFromCustomer(exists);
                return order;
            }
        }
        return null;
    }

    private CustomerOrder findExistingOrder(CustomOrderDTO dto) {
        return customerOrderService.getAll().stream()
                .filter(customerOrder -> isMatchingOrder(dto, customerOrder))
                .findFirst()
                .orElse(null);
    }

    private boolean isMatchingOrder(CustomOrderDTO dto, CustomerOrder order) {
        return order.getUserID().equals(dto.userID()) &&
                order.getBranchID().equals(dto.branchID()) &&
                order.getOrderStatus().equals(OrderStatus.NOT_CONFIRMED);
    }

    private boolean isFoodFromCurrentBrand(Order order, UUID branchId) {
        Branch branch = branchService.getByID(branchId);
        return foodBrandMappingService.isFoodFromBrand(order.getFoodID(), branch.getBrandID());
    }

    private Order findOrderWithSameFood(Order order) {
        return getAll().stream()
                .filter(existingOrder -> existingOrder.getFoodID().equals(order.getFoodID()))
                .findFirst()
                .orElse(null);
    }

    private Order createNewOrder(Order order, CustomerOrder existingOrder) {
        order.setCustomerOrderID(existingOrder.getId());
        add(order);
        return order;
    }

    private void updateOrderQuantityAndPrice(Order foundOrder, Order newOrder) {
        foundOrder.setFoodQuantity(foundOrder.getFoodQuantity() + newOrder.getFoodQuantity());
        Food food = foodService.getByID(foundOrder.getFoodID());
        foundOrder.setFoodPrice(new BigDecimal(foundOrder.getFoodQuantity() * food.getPrice().longValue()));
        update(foundOrder);
    }

    private void removeAllOrdersFromCustomer(CustomerOrder customerOrder) {
        getAll().stream()
                .filter(existingOrder -> existingOrder.getCustomerOrderID().equals(customerOrder.getId()))
                .forEach(o -> remove(o.getId()));
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
        List<Order> orders = getAll();
        orders.stream()
                .filter(o -> o.getId().equals(order.getId()))
                .forEach((o) -> {
                    o.setFoodID(order.getFoodID());
                    o.setFoodPrice(order.getFoodPrice());
                    o.setFoodQuantity(order.getFoodQuantity());
                    o.setCustomerOrderID(order.getCustomerOrderID());
                });
        repository.save(orders);
        return true;
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
