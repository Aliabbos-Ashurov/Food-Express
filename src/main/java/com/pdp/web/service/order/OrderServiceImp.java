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
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Singleton service class for managing orders.
 * Implements thread-safe lazy initialization with double-checked locking.
 *
 * @author Nishonov Doniyor
 * @see Order
 * @see CustomerOrder
 * @see Branch
 * @see Food
 * @see BranchService
 * @see CustomerOrderService
 * @see FoodService
 * @see FoodBrandMappingService
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderServiceImp implements OrderService {
    CustomerOrderService customerOrderService = ThreadSafeBeansContainer.customerOrderServiceThreadLocal.get();
    FoodBrandMappingService foodBrandMappingService = ThreadSafeBeansContainer.foodBrandMappingServiceThreadLocal.get();
    BranchService branchService = ThreadSafeBeansContainer.branchServiceThreadLocal.get();
    FoodService foodService = ThreadSafeBeansContainer.foodServiceThreadLocal.get();
    private static volatile OrderServiceImp instance;

    /**
     * Retrieves the singleton instance of OrderServiceImp.
     *
     * @return The singleton instance of OrderServiceImp.
     */
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
    public void clearByCustomer(UUID customerOrderID) {
        List<Order> orders = getAll();
        List<Order> orderList = orders.stream().filter(order -> Objects.equals(order.getCustomerOrderID(), customerOrderID))
                .toList();
        orders.removeAll(orderList);
        repository.save(orders);
    }

    /**
     * Retrieves orders associated with a specific customer order identified by its UUID.
     *
     * @param customerOrderID The UUID of the customer order.
     * @return A list of orders associated with the customer order.
     */
    @Override
    public List<Order> getOrdersByCustomerID(UUID customerOrderID) {
        List<Order> orderList = getAll();
        return orderList.stream()
                .filter(order -> order.getCustomerOrderID().equals(customerOrderID))
                .toList();
    }

    /**
     * Retrieves an existing order identified by its UUID, or creates a new order based on the provided CustomOrderDTO.
     *
     * @param dto   The CustomOrderDTO containing information about the custom order.
     * @param order The existing order, if available.
     * @return The retrieved or newly created Order object.
     */
    @Override
    public Order getOrCreate(CustomOrderDTO dto, Order order) {
        CustomerOrder exists = findExistingOrder(dto);
        if (Objects.nonNull(exists)) {
            boolean foodFromCurrentBrand = isFoodFromCurrentBrand(order, exists.getBranchID());
            if (foodFromCurrentBrand) {
                Order orderWithSameFood = findOrderWithSameFood(order, exists);
                if (Objects.nonNull(orderWithSameFood)) return updateOrderQuantityAndPrice(orderWithSameFood, order);
                else return createNewOrder(order, exists);
            } else {
                removeAllOrdersFromCustomer(exists);
                customerOrderService.remove(exists.getId());
                CustomerOrder build = CustomerOrder.builder()
                        .userID(dto.userID())
                        .branchID(dto.branchID())
                        .build();
                customerOrderService.add(build);
                order.setCustomerOrderID(build.getId());
                add(order);
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

    private Order findOrderWithSameFood(Order order, CustomerOrder customerOrder) {
        return getAll().stream()
                .filter(existingOrder -> existingOrder.getFoodID().equals(order.getFoodID())
                        && existingOrder.getCustomerOrderID().equals(customerOrder.getId()))
                .findFirst()
                .orElse(null);
    }

    private Order createNewOrder(Order order, CustomerOrder existingOrder) {
        order.setCustomerOrderID(existingOrder.getId());
        add(order);
        return order;
    }

    private Order updateOrderQuantityAndPrice(Order foundOrder, Order newOrder) {
        foundOrder.setFoodQuantity(foundOrder.getFoodQuantity() + newOrder.getFoodQuantity());
        Food food = foodService.getByID(foundOrder.getFoodID());
        foundOrder.setFoodPrice(new BigDecimal(foundOrder.getFoodQuantity() * food.getPrice().longValue()));
        update(foundOrder);
        return foundOrder;
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
    public boolean add(@NotNull Order order) {
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
        Optional<Order> first = orders.stream()
                .filter(o -> o.getId().equals(order.getId()))
                .findFirst();
        if (first.isPresent()) {
            updateOrderData(first.get(), order);
            repository.save(orders);
            return true;
        }
        return false;
    }

    private void updateOrderData(Order order, Order updated) {
        order.setFoodID(order.getFoodID());
        order.setFoodPrice(order.getFoodPrice());
        order.setFoodQuantity(order.getFoodQuantity());
        order.setCustomerOrderID(order.getCustomerOrderID());
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
