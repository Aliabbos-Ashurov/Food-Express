package com.pdp.web.repository.address;

import com.pdp.config.SQLConfiguration;
import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.address.Address;
import com.pdp.web.repository.BaseRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import sql.helper.SQLHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
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
 * This class is a singleton and can be accessed via {@link }.
 *
 * @author Aliabbos Ashurov
 * @see Address for the entity that {@code AddressRepository} manages.
 * @see BaseRepository for base repository interface.
 * @since 04/May/2024 15:34
 */
public class AddressRepository implements BaseRepository<Address, List<Address>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a new address to the repository and persists changes.
     *
     * @param address The Address object to add.
     * @return True if the address is added successfully, false otherwise.
     */
    @Override
    @SneakyThrows
    public boolean add(@NonNull Address address) {
        return sql.executeUpdate("INSERT INTO web.address(city,street,apartment_number,house_number) VALUES(?,?,?,?);",
                address.getCity(), address.getStreet(),
                address.getHouseNumber(), address.getApartmentNumber()) > 0;
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
    @SneakyThrows
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.address WHERE id = ?;", id) > 0;
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
        return getAll().stream()
                .filter((address -> address.getId().equals(id)))
                .findFirst().orElse(null);
    }

    /**
     * Obtains a complete list of {@link Address} entities managed by the repository.
     *
     * @return A {@link List} of all {@link Address} entities within the repository.
     */
    @SneakyThrows
    @Override
    public List<Address> getAll() {
        ResultSet rs = sql.executeQuery("SELECT * FROM web.address;");
        List<Address> addresses = new ArrayList<>();
        while (rs.next()) {
            Address address = new Address(rs.getString(2), rs.getString(3), rs.getInt(5), rs.getInt(6));
            address.setId(UUID.fromString(rs.getString(1)));
            address.setCreatedAt(rs.getTimestamp(4).toLocalDateTime());
            addresses.add(address);
        }
        return addresses;
    }
}
