package com.pdp.web.repository.customerOrder;

import com.pdp.config.SQLConfiguration;
import com.pdp.enums.OrderStatus;
import com.pdp.enums.PaymentType;
import com.pdp.web.model.customerOrder.CustomerOrder;
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
 * Provides CRUD operations for CustomerOrder entities, encapsulating the logic required
 * to add, remove, retrieve, and list all customer orders persisted in the system.
 * <p>
 * Leverages JsonSerializer for translating CustomerOrder objects to and from JSON, supporting
 * data persistence to a file defined by JsonFilePath.CUSTOMER_ORDER.
 * <p>
 * Suspends normal checks during exception handling, allowing unchecked exceptions to propagate,
 * as indicated by the use of @SneakyThrows. Error handling is targeted towards console logging
 * and returning alternative results when exception conditions are encountered.
 *
 * @author Aliabbos Ashurov
 * @see BaseRepository
 * @since 04/May/2024 16:37
 */
public class CustomerOrderRepository implements BaseRepository<CustomerOrder, List<CustomerOrder>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a CustomerOrder to the repository and persistently writes it to the JSON file.
     *
     * @param customerOrder The CustomerOrder to store.
     * @return True if the addition is successful, indicating the customerOrder
     * was added and the list was saved.
     */
    @Override
    @SneakyThrows
    public boolean add(@NonNull CustomerOrder customerOrder) {
        return sql.executeUpdate("INSERT INTO web.customer_order(user_id,branch_id,address_id,order_status,order_price,payment_type,deliverer_id,description_id) VALUES(?,?,?,?,?,?,?,?)",
                customerOrder.getUserID(), customerOrder.getBranchID(), customerOrder.getAddressID(), customerOrder.getOrderStatus(),
                customerOrder.getOrderPrice(), customerOrder.getPaymentType(), customerOrder.getDeliverID(), customerOrder.getDescriptionID()) > 0;
    }

    /**
     * Removes a CustomerOrder based on its UUID from the repository and writes
     * the updated list persistently to the JSON file.
     *
     * @param id The UUID of the customerOrder to remove.
     * @return True if the customerOrder was successfully found and removed, false otherwise.
     */
    @Override
    @SneakyThrows
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.customer_order WHERE id = ?;", id) > 0;
    }

    @Override
    @SneakyThrows
    public boolean update(@NonNull CustomerOrder customerOrder) {
        return sql.executeUpdate("UPDATE web.customer_order set user_id=?,branch_id=?,address_id=?,order_status=?,order_price=?,payment_type=?,deliverer_id=?,description_id=? WHERE id = ?;",
                customerOrder.getUserID(), customerOrder.getBranchID(), customerOrder.getAddressID(), String.valueOf(customerOrder.getOrderStatus()),
                customerOrder.getOrderPrice(), String.valueOf(customerOrder.getPaymentType()), customerOrder.getDeliverID(), customerOrder.getDescriptionID(), customerOrder.getDescriptionID()) > 0;
    }

    /**
     * Searches and retrieves a {@code CustomerOrder} from the repository using the
     * specified UUID. If the order is not found, `{@code null}` is returned. This
     * method reads from the in-memory list, which is synchronized with the JSON file.
     *
     * @param id The UUID of the {@code CustomerOrder} to retrieve.
     * @return The retrieved {@code CustomerOrder}, or {@code null} if not found.
     */
    @Override
    public CustomerOrder findById(@NonNull UUID id) {
        return getAll().stream()
                .filter(customerOrder -> customerOrder.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Retrieves all customer orders from the repository. This method reads from the
     * in-memory list, which reflects the state of the persisted JSON file.
     *
     * @return A List of all {@code CustomerOrder} objects
     */
    @Override
    @SneakyThrows
    public List<CustomerOrder> getAll() {
        List<CustomerOrder> customerOrders = new ArrayList<>();
        ResultSet rs = sql.executeQuery("SELECT * FROM web.customer_order;");
        while (rs.next()) {
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setId(UUID.fromString(rs.getString(1)));
            customerOrder.setUserID(UUID.fromString(rs.getString(rs.getString(2))));
            customerOrder.setBranchID(UUID.fromString(rs.getString(3)));
            customerOrder.setAddressID(UUID.fromString(rs.getString(4)));
            customerOrder.setOrderStatus(OrderStatus.valueOf(rs.getString(5)));
            customerOrder.setOrderPrice(BigDecimal.valueOf(rs.getDouble(6)));
            customerOrder.setPaymentType(PaymentType.valueOf(rs.getString(7)));
            customerOrder.setDeliverID(UUID.fromString(rs.getString(8)));
            customerOrder.setDescriptionID(UUID.fromString(rs.getString(9)));
            customerOrder.setCreatedAt(rs.getTimestamp(10).toLocalDateTime());
            customerOrders.add(customerOrder);
        }
        return customerOrders;
    }
}
