package com.pdp.web.repository.address;

import com.pdp.web.model.address.Address;
import com.pdp.web.repository.BaseRepository;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Provides repository operations for Address entities, enabling CRUD operations
 * to be performed on address data. This includes adding, removing, finding by ID,
 * and retrieving all address records.
 * <p>
 * Utilizes JsonSerializer for handling serialization and deserialization of Address
 * objects for persistence to and from a JSON file located at the path provided by
 * JsonFilePath.ADDRESS.
 * <p>
 * The repository maintains an in-memory list of address objects for runtime data
 * access. Changes to the addresses list are persisted to the JSON file via JsonSerializer.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 15:34
 */
public class AddressRepository implements BaseRepository<Address, List<Address>> {
    private static volatile AddressRepository instance;
    private static JsonSerializer<Address> jsonSerializer;

    private AddressRepository() {
    }

    /**
     * Gets the singleton instance of AddressRepository.
     *
     * @return The singleton instance of AddressRepository.
     */
    public static AddressRepository getInstance() {
        if (instance == null) {
            synchronized (AddressRepository.class) {
                if (instance == null) {
                    instance = new AddressRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_ADDRESS));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new address to the repository and persists changes.
     *
     * @param address The Address object to add.
     * @return True if the address is added successfully, false otherwise.
     */
    @Override
    public boolean add(@NonNull Address address) {
        List<Address> addresses = load();
        addresses.add(address);
        save(addresses);
        return true;
    }

    /**
     * Removes an Address record from the repository based on the given ID and persists changes.
     *
     * @param id The UUID of the Address record to remove.
     * @return True if an address with the specified ID was found and removed, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<Address> addresses = load();
        boolean removed = addresses.removeIf(address -> address.getId().equals(id));
        if (removed) save(addresses);
        return removed;
    }

    /**
     * Finds an Address record by its ID.
     *
     * @param id The UUID of the Address to find.
     * @return The Address object if found, or null if not found.
     */
    @Override
    public Address findById(@NonNull UUID id) {
        List<Address> addresses = load();
        return addresses.stream()
                .filter((address -> address.getId().equals(id)))
                .findFirst().orElse(null);
    }

    /**
     * Retrieves all addresses from the repository.
     *
     * @return A list of all Address objects in the repository.
     */
    @Override
    public List<Address> getAll() {
        return load();
    }

    @SneakyThrows
    @Override
    public List<Address> load() {
        return jsonSerializer.read(Address.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<Address> list) {
        jsonSerializer.write(list);
    }
}
