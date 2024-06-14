package com.pdp.web.service.address;

import com.pdp.web.model.address.Address;
import com.pdp.web.repository.address.AddressRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * Service interface for address-related operations.
 * This interface extends the {@code BaseService} and specializes it for {@code Address} entities.
 * It provides abstracted data access and business logic methods to manage address entities such as
 * retrieving, adding, updating, or deleting addresses in the system.
 * <p>
 * The generic base interface for generic CRUD operations this interface extends from.
 * The domain model class this service relates to.
 * The data access repository that this service uses for address entity interactions.
 *
 * @see BaseService
 * @see Address
 * @see AddressRepository
 */
public interface AddressService extends BaseService<Address, List<Address>> {
    AddressRepository repository = new AddressRepository();
}
