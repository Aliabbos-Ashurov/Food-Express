package com.pdp.backend.web.service.address;

import com.pdp.backend.web.model.address.Address;
import com.pdp.backend.web.repository.address.AddressRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

/**
 * Service interface for address-related operations.
 * Provides methods to manage address entities such as retrieving, adding, updating, or deleting addresses.
 */
public interface AddressService extends BaseService<Address, List<Address>> {
    AddressRepository repository = new AddressRepository();
}
