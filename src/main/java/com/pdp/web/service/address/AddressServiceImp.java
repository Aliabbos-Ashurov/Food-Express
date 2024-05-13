package com.pdp.web.service.address;

import com.pdp.utils.Validator;
import com.pdp.web.model.address.Address;
import com.pdp.web.repository.address.AddressRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Singleton service class implementing the AddressService interface.
 * Provides methods for address management such as adding, removing, updating, and searching addresses.
 * <p>
 * This service class uses lazy initialization to ensure a single instance of AddressServiceImp is created.
 * Thread-safe creation of the singleton instance is achieved using double-checked locking.
 * </p>
 * <p>
 * This service class delegates persistence operations to {@link AddressRepository}.
 * </p>
 *
 * @author Aliabbos Ashurov
 * @see AddressService
 * @see AddressRepository
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressServiceImp implements AddressService {
    private static volatile AddressServiceImp instance;

    /**
     * Retrieves the singleton instance of AddressServiceImp.
     *
     * @return The singleton instance of AddressServiceImp.
     */
    public static AddressServiceImp getInstance() {
        if (instance == null)
            synchronized (AddressServiceImp.class) {
                if (instance == null) {
                    instance = new AddressServiceImp();
                }
            }
        return instance;
    }

    /**
     * Adds a new address to the repository.
     *
     * @param address The address to add.
     * @return true if the address was added successfully, false otherwise.
     */
    @Override
    public boolean add(@NonNull Address address) {
        return repository.add(address);
    }

    /**
     * Removes an address from the repository by its ID.
     *
     * @param id The ID of the address to remove.
     * @return true if the address was removed successfully, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an address in the repository.
     *
     * @param address The updated address object.
     * @return true if the address was updated successfully, false otherwise.
     */
    @Override
    public boolean update(@NonNull Address address) {
        List<Address> addresses = getAll();
        Optional<Address> optionalAddress = addresses.stream()
                .filter(o -> address.getId().equals(o.getId()))
                .findFirst();
        if (optionalAddress.isPresent()) {
            updateAddressData(optionalAddress.get(), address);
            repository.save(addresses);
            return true;
        }
        return false;
    }

    private void updateAddressData(Address current, Address updated) {
        current.setCity(updated.getCity());
        current.setStreet(updated.getStreet());
        current.setHouseNumber(updated.getHouseNumber());
        current.setApartmentNumber(updated.getApartmentNumber());
    }

    /**
     * Searches for addresses that match the specified query.
     *
     * @param query The search query.
     * @return A list of addresses that match the query.
     */
    @Override
    public List<Address> search(@NonNull String query) {
        List<Address> addresses = getAll();
        return addresses.stream()
                .filter(address -> Validator.isValid(address.getStreet(), query))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an address by its ID from the repository.
     *
     * @param id The ID of the address to retrieve.
     * @return The address object if found, otherwise null.
     */
    @Override
    public Address getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all addresses.
     *
     * @return A list of all addresses in the repository.
     */
    @Override
    public List<Address> getAll() {
        return repository.getAll();
    }
}
