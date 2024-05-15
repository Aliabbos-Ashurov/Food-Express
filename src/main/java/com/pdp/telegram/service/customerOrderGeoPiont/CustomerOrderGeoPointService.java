package com.pdp.telegram.service.customerOrderGeoPiont;

import com.pdp.telegram.model.customerOrderGeoPoint.CustomerOrderGeoPoint;
import com.pdp.telegram.repository.customerOrderGeoPoint.CustomerOrderGeoPointRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * This interface defines the service layer for managing customer order geo points.
 * It extends the BaseService interface.
 *
 * @param <CustomerOrderGeoPoint>       The type representing a customer order geo point.
 * @param <List<CustomerOrderGeoPoint>> The type representing a list of customer order geo points.
 * @author Doniyor Nishonov
 * @since 14th May 2024, 15:10
 */
public interface CustomerOrderGeoPointService extends BaseService<CustomerOrderGeoPoint, List<CustomerOrderGeoPoint>> {
    /**
     * The repository instance for accessing customer order geo point data.
     */
    CustomerOrderGeoPointRepository repository = CustomerOrderGeoPointRepository.getInstance();
}
