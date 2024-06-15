package com.pdp.telegram.repository.customerOrderGeoPoint;


import com.pdp.config.SQLConfiguration;
import com.pdp.telegram.model.customerOrderGeoPoint.CustomerOrderGeoPoint;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Repository class for managing customer order geo points.
 * Handles data storage and retrieval.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 14:37
 */
public class CustomerOrderGeoPointRepository implements BaseRepository<CustomerOrderGeoPoint, List<CustomerOrderGeoPoint>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a customer order geo point to the repository.
     *
     * @param customerOrderGeoPoint The customer order geo point to add.
     * @return True if the addition was successful, false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean add(@NonNull CustomerOrderGeoPoint customerOrderGeoPoint) {
        return sql.executeUpdate("INSERT INTO telegram.customer_order_geo_point(lattidue,longtidue) VALUES (?,?);",
                customerOrderGeoPoint.getLattidue(), customerOrderGeoPoint.getLongtidue()) > 0;
    }

    /**
     * Removes a customer order geo point from the repository.
     *
     * @param id The ID of the customer order geo point to remove.
     * @return True if the removal was successful, false otherwise.
     */
    @SneakyThrows
    @Override
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM telegram.customer_order_geo_point WHERE id = ?;", id) > 0;
    }

    @Override
    @SneakyThrows
    public boolean update(@NonNull CustomerOrderGeoPoint cGeoPoint) {
        return sql.executeUpdate("UPDATE telegram.customer_order_geo_point set lattidue=?,longtidue=? WHERE id = ?;",
                cGeoPoint.getLattidue(), cGeoPoint.getLongtidue(), cGeoPoint.getId()) > 0;
    }

    /**
     * Finds a customer order geo point by its ID.
     *
     * @param id The ID of the customer order geo point to find.
     * @return The customer order geo point with the specified ID, or null if not found.
     */
    @Override
    public CustomerOrderGeoPoint findById(@NonNull UUID id) {
        return getAll().stream()
                .filter(customerOrderGeoPoint -> customerOrderGeoPoint.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Retrieves all customer order geo points from the repository.
     *
     * @return A list of all customer order geo points.
     */
    @SneakyThrows
    @Override
    public List<CustomerOrderGeoPoint> getAll() {
        ResultSet resultSet = sql.executeQuery("SELECT * FROM telegram.customer_order_geo_point;");
        List<CustomerOrderGeoPoint> customerOrderGeoPoints = new ArrayList<>();
        while (resultSet.next()) {
            CustomerOrderGeoPoint customerOrderGeoPoint = new CustomerOrderGeoPoint();
            customerOrderGeoPoint.setId(UUID.fromString(resultSet.getString("id")));
            customerOrderGeoPoint.setLattidue(resultSet.getFloat("lattidue"));
            customerOrderGeoPoint.setLongtidue(resultSet.getFloat("longtidue"));
            customerOrderGeoPoint.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            customerOrderGeoPoints.add(customerOrderGeoPoint);
        }
        return customerOrderGeoPoints;
    }
}
