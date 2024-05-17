package com.pdp.web.service.transport;

import com.pdp.utils.Validator;
import com.pdp.web.model.transport.Transport;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Singleton service class for managing transport entities.
 * Implements thread-safe lazy initialization with double-checked locking.
 *
 * @see Transport
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransportServiceImp implements TransportService {
    private static volatile TransportServiceImp instance;

    /**
     * Retrieves the singleton instance of TransportServiceImp.
     *
     * @return The singleton instance of TransportServiceImp.
     */
    public static TransportServiceImp getInstance() {
        if (instance == null) {
            synchronized (TransportServiceImp.class) {
                if (instance == null) {
                    instance = new TransportServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new transport entity to the repository.
     *
     * @param transport the {@link Transport} object to be added
     * @return true if the transport was successfully added, false otherwise
     */
    @Override
    public boolean add(@NotNull Transport transport) {
        return repository.add(transport);
    }

    /**
     * Removes a transport entity from the repository using its UUID.
     *
     * @param id the UUID of the transport to be removed
     * @return true if the transport was successfully removed, false otherwise
     */
    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing transport entity in the repository. Currently not implemented.
     *
     * @param transport the {@link Transport} object to update
     * @return false, indicating the operation is not supported yet
     */
    @Override
    public boolean update(Transport transport) {
        return false;
    }

    /**
     * Searches for transport entities that match a given query string.
     *
     * @param query the query string to match against transport entity display names
     * @return a list of {@link Transport} objects that match the query
     */
    @Override
    public List<Transport> search(String query) {
        return getAll().stream()
                .filter(t -> Validator.isValid(t.getDisplayName(), query))
                .toList();
    }

    /**
     * Retrieves a transport entity by its UUID.
     *
     * @param id the UUID of the transport to retrieve
     * @return the {@link Transport} object, or null if no transport is found
     */
    @Override
    public Transport getByID(UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all transport entities available in the repository.
     *
     * @return a list of all {@link Transport} objects
     */
    @Override
    public List<Transport> getAll() {
        return repository.getAll();
    }
}
