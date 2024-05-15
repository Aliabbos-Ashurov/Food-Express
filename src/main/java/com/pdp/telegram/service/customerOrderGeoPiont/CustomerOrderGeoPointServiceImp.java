package com.pdp.telegram.service.customerOrderGeoPiont;

import com.pdp.telegram.model.customerOrderGeoPoint.CustomerOrderGeoPoint;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  15:11
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerOrderGeoPointServiceImp implements CustomerOrderGeoPointService {
    private static volatile CustomerOrderGeoPointServiceImp instance;

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

    @Override
    public boolean add(@NonNull CustomerOrderGeoPoint customerOrderGeoPoint) {
        return repository.add(customerOrderGeoPoint);
    }

    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    @Override
    public boolean update(@NonNull CustomerOrderGeoPoint customerOrderGeoPoint) {
        return false;
    }

    @Override
    public List<CustomerOrderGeoPoint> search(@NonNull String query) {
        return List.of();
    }

    @Override
    public CustomerOrderGeoPoint getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<CustomerOrderGeoPoint> getAll() {
        return repository.getAll();
    }
}
