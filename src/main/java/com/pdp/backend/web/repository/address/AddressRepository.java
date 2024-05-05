package com.pdp.backend.web.repository.address;

import com.pdp.backend.web.config.path.ResoucePath;
import com.pdp.backend.web.model.address.Address;
import com.pdp.backend.web.repository.BaseRepository;
import com.pdp.json.serializer.JsonSerializer;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Provides repository operations for Address entities, enabling CRUD operations
 * to be performed on address data. This includes adding, removing, finding by ID,
 * and retrieving all address records.
 * <p>
 * Utilizes JsonSerializer for handling serialization and deserialization of Address
 * objects for persistence to and from a JSON file located at the path provided by
 * ResourcePath.ADDRESS.
 * <p>
 * The repository maintains an in-memory list of address objects for runtime data
 * access. Changes to the addresses list are persisted to the JSON file via JsonSerializer.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 15:34
 */
public class AddressRepository implements BaseRepository<Address> {
    private final JsonSerializer<Address> jsonSerializer;
    private final List<Address> addresses;

    public AddressRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(ResoucePath.ADDRESS));
        this.addresses = load();
    }

    /**
     * Adds a new address to the repository and persists changes.
     *
     * @param address The Address object to add.
     * @return True if the address is added successfully, false otherwise.
     */
    @Override
    public boolean add(Address address) {
        addresses.add(address);
        save();
        return true;
    }

    /**
     * Removes an Address record from the repository based on the given ID and persists changes.
     *
     * @param id The UUID of the Address record to remove.
     * @return True if an address with the specified ID was found and removed, false otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        boolean removed = addresses.removeIf(address -> address.getId().equals(id));
        if (removed) save();
        return removed;
    }

    @Override
    public Address findById(UUID id) {
        return addresses.stream()
                .filter((address -> address.getId().equals(id)))
                .findFirst().orElse(null);
    }

    @Override
    public List<Address> getAll() {
        return Collections.unmodifiableList(addresses);
    }

    @Override
    public List<Address> load() {
        try {
            return jsonSerializer.read(Address.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(addresses);
    }
}
