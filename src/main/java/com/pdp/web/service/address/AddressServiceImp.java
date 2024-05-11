package com.pdp.web.service.address;

import com.pdp.utils.Validator;
import com.pdp.web.model.address.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Singleton service class implementing the AddressService interface.
 * Provides methods for address management such as adding, removing, updating, and searching addresses.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressServiceImp implements AddressService {
    private static volatile AddressServiceImp instance;

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
    public boolean add(Address address) {
        return repository.add(address);
    }

    /**
     * Removes an address from the repository by its ID.
     *
     * @param id The ID of the address to remove.
     * @return true if the address was removed successfully, false otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    @Override
    public boolean update(Address object) {
        return false;
    }

    /**
     * Searches for addresses that match the specified query.
     *
     * @param query The search query.
     * @return A list of addresses that match the query.
     */
    @Override
    public List<Address> search(String query) {
        List<Address> addresses = getAll();
        return addresses.stream()
                .filter(address -> Validator.isValid(address.getStreet(), query))
                .collect(Collectors.toList());
    }

    @Override
    public Address getByID(UUID id) {
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
