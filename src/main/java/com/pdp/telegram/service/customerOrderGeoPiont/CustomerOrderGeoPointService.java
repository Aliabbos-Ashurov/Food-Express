package com.pdp.telegram.service.customerOrderGeoPiont;

import com.pdp.telegram.model.customerOrderGeoPoint.CustomerOrderGeoPoint;
import com.pdp.telegram.repository.customerOrderGeoPoint.CustomerOrderGeoPointRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  15:10
 **/
public interface CustomerOrderGeoPointService extends BaseService<CustomerOrderGeoPoint, List<CustomerOrderGeoPoint>> {
    CustomerOrderGeoPointRepository repository = CustomerOrderGeoPointRepository.getInstance();
}
