package com.pdp.web.repository.order;

import com.pdp.config.SQLConfiguration;
import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.order.Order;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import sql.helper.SQLHelper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Provides the data access operations for {@code Order} entities within the application, enabling
 * basic create, read, update, and delete (CRUD) capabilities as defined by the {@code BaseRepository} interface.
 * This repository utilizes {@code JsonSerializer} to serialize the {@code Order} objects to JSON and store
 * them in a file for persistent storage.
 * <p>
 * The repository ensures all orders are kept synchronized with their persistent JSON representation,
 * allowing for a robust and recoverable data storage solution.
 *
 * @author Aliabbos Ashurov
 * @see BaseRepository
 * @see JsonSerializer
 * @see Order
 * @since 04/May/2024 16:57
 */
public class OrderRepository implements BaseRepository<Order, List<Order>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a new order to the repository. The order is also immediately persisted to the JSON file.
     *
     * @param order The new {@code Order} object to be added.
     * @return True if the order was successfully added to the repository; false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean add(@NonNull Order order) {
        return sql.executeUpdate("INSERT INTO web.orders(food_id,food_price,food_quantity,customer_order_id) VALUES (?,?,?,?);",
                order.getFoodID(), order.getFoodPrice(), order.getFoodQuantity(), order.getCustomerOrderID()) > 0;
    }

    /**
     * Removes an order from the repository based on its ID.
     *
     * @param id The ID of the order to remove.
     * @return {@code true} if the order was found and removed; {@code false} otherwise.
     */
    @SneakyThrows
    @Override
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.orders WHERE id = ?;", id) > 0;
    }

    @Override
    @SneakyThrows
    public boolean update(@NonNull Order order) {
        return sql.executeUpdate("UPDATE web.orders set food_id=?,food_price=?,food_quantity,customer_order_id=? WHERE id = ?;",
                order.getFoodID(), order.getFoodPrice(), order.getFoodQuantity(), order.getCustomerOrderID(), order.getId()) > 0;
    }

    /**
     * Retrieves an order from the repository based on its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The {@code Order} object corresponding to the given ID, or {@code null} if not found.
     */
    @Override
    public Order findById(@NonNull UUID id) {
        return getAll().stream().filter(order -> order.getId().equals(id)).findFirst().orElse(null);
    }

    @SneakyThrows
    @Override
    public List<Order> getAll() {
        ResultSet resultSet = sql.executeQuery("SELECT * FROM web.orders;");
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            Order order = new Order();
            order.setId(UUID.fromString(resultSet.getString("id")));
            order.setFoodID(UUID.fromString(resultSet.getString("food_id")));
            order.setFoodPrice(BigDecimal.valueOf(Double.parseDouble(resultSet.getString("food_price"))));
            order.setFoodQuantity(resultSet.getInt("food_quantity"));
            order.setCustomerOrderID(UUID.fromString(resultSet.getString("customer_order_id")));
            order.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            orders.add(order);
        }
        return orders;
    }
    @SneakyThrows
    public void clearByCustomer(UUID customerOrderId) {
        sql.executeUpdate("DELETE from web.orders WHERE customer_order_id = ?;", customerOrderId);
    }
}
