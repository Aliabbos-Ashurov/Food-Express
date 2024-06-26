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
import java.time.LocalDateTime;
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
        return sql.executeUpdate("INSERT INTO web.customer_order(id,user_id,branch_id,address_id,order_status,customer_order_geo_point_id,order_price,payment_type,deliverer_id,description_id) VALUES(?,?,?,?,?,?,?,?,?,?)",
                customerOrder.getId(),customerOrder.getUserID(), customerOrder.getBranchID(), customerOrder.getAddressID(), String.valueOf(customerOrder.getOrderStatus()), customerOrder.getCustomerOrderGeoPointID(),
                customerOrder.getOrderPrice(), String.valueOf(customerOrder.getPaymentType()), customerOrder.getDeliverID(), customerOrder.getDescriptionID()) > 0;
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
        return sql.executeUpdate("UPDATE web.customer_order set user_id=?,branch_id=?,address_id=?,customer_order_geo_point_id=?,order_status=?,order_price=?,payment_type=?,deliverer_id=?,description_id=? WHERE id = ?;",
                customerOrder.getUserID(), customerOrder.getBranchID(), customerOrder.getAddressID(),customerOrder.getCustomerOrderGeoPointID(), String.valueOf(customerOrder.getOrderStatus()),
                customerOrder.getOrderPrice(), String.valueOf(customerOrder.getPaymentType()), customerOrder.getDeliverID(), customerOrder.getDescriptionID(), customerOrder.getId()) > 0;
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

            UUID id = rs.getString(1) != null ? UUID.fromString(rs.getString(1)) : null;
            UUID userID = rs.getString(2) != null ? UUID.fromString(rs.getString(2)) : null;
            UUID branchID = rs.getString(3) != null ? UUID.fromString(rs.getString(3)) : null;
            UUID addressID = rs.getString(4) != null ? UUID.fromString(rs.getString(4)) : null;
            OrderStatus orderStatus = rs.getString(5) != null ? OrderStatus.valueOf(rs.getString(5)) : null;
            BigDecimal orderPrice = rs.getObject(6) != null ? BigDecimal.valueOf(rs.getDouble(6)) : null;
            PaymentType paymentType = null;
            if (rs.getString(7) != null) {
                try {
                    paymentType = PaymentType.valueOf(rs.getString(7));
                } catch (IllegalArgumentException ignored) {
                }
            }
            UUID customerOrderGeoPoint = rs.getString(11) != null ? UUID.fromString(rs.getString(11)) : null;
            UUID deliverID = rs.getString(8) != null ? UUID.fromString(rs.getString(8)) : null;
            UUID descriptionID = rs.getString(9) != null ? UUID.fromString(rs.getString(9)) : null;
            LocalDateTime createdAt = rs.getTimestamp(10) != null ? rs.getTimestamp(10).toLocalDateTime() : null;

            customerOrder.setId(id);
            customerOrder.setUserID(userID);
            customerOrder.setBranchID(branchID);
            customerOrder.setAddressID(addressID);
            customerOrder.setOrderStatus(orderStatus);
            customerOrder.setOrderPrice(orderPrice);
            customerOrder.setCustomerOrderGeoPointID(customerOrderGeoPoint);
            customerOrder.setPaymentType(paymentType);
            customerOrder.setDeliverID(deliverID);
            customerOrder.setDescriptionID(descriptionID);
            customerOrder.setCreatedAt(createdAt);

            customerOrders.add(customerOrder);
        }
        return customerOrders;
    }

}

