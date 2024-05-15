package com.pdp.telegram.service.customerOrderGeoPiont;

import com.pdp.telegram.model.customerOrderGeoPoint.CustomerOrderGeoPoint;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Implementation class for managing customer order geo points.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 15:11
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerOrderGeoPointServiceImp implements CustomerOrderGeoPointService {
    private static volatile CustomerOrderGeoPointServiceImp instance;

    /**
     * Private constructor to prevent instantiation from outside.
     */
    public static CustomerOrderGeoPointServiceImp getInstance() {
        if (instance == null) {
            synchronized (CustomerOrderGeoPointServiceImp.class) {
                if (instance == null) {
                    instance = new CustomerOrderGeoPointServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Adds a customer order geo point.
     *
     * @param customerOrderGeoPoint The customer order geo point to add.
     * @return True if the addition was successful, false otherwise.
     */
    @Override
    public boolean add(@NonNull CustomerOrderGeoPoint customerOrderGeoPoint) {
        return repository.add(customerOrderGeoPoint);
    }

    /**
     * Removes a customer order geo point by its ID.
     *
     * @param id The ID of the customer order geo point to remove.
     * @return True if the removal was successful, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates a customer order geo point.
     *
     * @param customerOrderGeoPoint The customer order geo point to update.
     * @return True if the update was successful, false otherwise.
     */
    @Override
    public boolean update(@NonNull CustomerOrderGeoPoint customerOrderGeoPoint) {
        return false;
    }

    /**
     * Searches for customer order geo points based on a query.
     *
     * @param query The search query.
     * @return A list of customer order geo points matching the query.
     */
    @Override
    public List<CustomerOrderGeoPoint> search(@NonNull String query) {
        return List.of();
    }

    /**
     * Retrieves a customer order geo point by its ID.
     *
     * @param id The ID of the customer order geo point to retrieve.
     * @return The customer order geo point with the specified ID.
     */
    @Override
    public CustomerOrderGeoPoint getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all customer order geo points.
     *
     * @return A list of all customer order geo points.
     */
    @Override
    public List<CustomerOrderGeoPoint> getAll() {
        return repository.getAll();
    }
}
