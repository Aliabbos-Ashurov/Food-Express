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
 * Provides repository operations for {@link Address} entities, enabling CRUD operations
 * to be conducted on address data. This includes adding, removing, finding by ID,
 * and retrieving all address records.
 * <p>
 * Utilizes {@link JsonSerializer} for the serialization and deserialization of
 * {@link Address} objects for persistence to and from a JSON file located at the
 * path provided by {@code JsonFilePath.ADDRESS}.
 * <p>
 * The repository maintains an in-memory list of address objects for runtime data
 * access. Changes to the addresses list are persisted to the JSON file via
 * {@link JsonSerializer}.
 * <p>
 * This class is a singleton and can be accessed via {@link #getInstance()}.
 *
 * @author Aliabbos Ashurov
 * @see Address for the entity that {@code AddressRepository} manages.
 * @see BaseRepository for base repository interface.
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
     * Removes the {@link Address} with the specified unique identifier from the repository.
     * The removal is persisted by updating the repository's JSON file. This method is
     * idempotent; calling it on an identifier not present in the repository has no effect.
     *
     * @param id The unique identifier of the {@link Address} to be removed.
     * @return {@code true} if an address with the specified identifier was found and removed;
     * {@code false} otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<Address> addresses = load();
        boolean removed = addresses.removeIf(address -> address.getId().equals(id));
        if (removed) save(addresses);
        return removed;
    }

    /**
     * Retrieves an {@link Address} entity by its unique identifier. If no such address
     * exists within the repository, this method returns {@code null}.
     *
     * @param id The unique identifier of the {@link Address} to locate.
     * @return The {@link Address} entity if found; otherwise, {@code null}.
     */
    @Override
    public Address findById(@NonNull UUID id) {
        List<Address> addresses = load();
        return addresses.stream()
                .filter((address -> address.getId().equals(id)))
                .findFirst().orElse(null);
    }

    /**
     * Obtains a complete list of {@link Address} entities managed by the repository.
     *
     * @return A {@link List} of all {@link Address} entities within the repository.
     */
    @Override
    public List<Address> getAll() {
        return load();
    }

    /**
     * Reads a collection of {@link Address} entities from the repository's data store.
     * This method depends on {@link JsonSerializer} to read a list of addresses from
     * the corresponding JSON file and deserialize them.
     *
     * @return A list containing all deserialized {@link Address} entities.
     * @throws Exception if there is an error during the deserialization process.
     */
    @SneakyThrows
    @Override
    public List<Address> load() {
        return jsonSerializer.read(Address.class);
    }


    /**
     * Persists the current state of the repository's {@link Address} entities to the
     * data store. This method utilizes {@link JsonSerializer} to serialize and write
     * the list of addresses to the repository's JSON file.
     *
     * @param list The list of {@link Address} entities to be serialized and written
     *             to the persistent data store.
     * @throws Exception if there is an error during the serialization or write process.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<Address> list) {
        jsonSerializer.write(list);
    }
}
