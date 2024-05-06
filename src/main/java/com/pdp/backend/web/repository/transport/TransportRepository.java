package com.pdp.backend.web.repository.transport;

import com.pdp.backend.web.config.jsonFilePath.JsonFilePath;
import com.pdp.backend.web.model.transport.Transport;
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
 * Manages the persistence and retrieval of {@link Transport} objects using JSON serialization.
 * This class implements the {@link BaseRepository} interface to provide CRUD operations on
 * transport entities stored in a JSON file.
 *
 * The repository is backed by a list that is synchronized with a local JSON file, allowing for
 * persistent storage and retrieval operations on Transport entities.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 17:03
 */
public class TransportRepository implements BaseRepository<Transport> {
    private final JsonSerializer<Transport> jsonSerializer;
    private final List<Transport> transports;

    /**
     * Constructs a new TransportRepository, initializing the serializer with the file path for transport data,
     * and loading the existing transport data from this file into memory.
     */
    public TransportRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(PATH_TRANSPORT));
        this.transports = load();
    }

    /**
     * Adds a {@link Transport} object to the repository and saves the updated list to the JSON file.
     *
     * @param transport The transport entity to add.
     * @return Always returns {@code true}, indicating the transport was added successfully.
     */
    @Override
    public boolean add(Transport transport) {
        transports.add(transport);
        save();
        return true;
    }

    /**
     * Removes a transport entity by its UUID and saves the updated list if the entity was successfully removed.
     *
     * @param id The UUID of the transport entity to be removed.
     * @return {@code true} if the transport entity was found and removed, {@code false} otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        boolean removed = transports.removeIf(transport -> transport.getId().equals(id));
        if (removed) save();
        return removed;
    }

    /**
     * Finds a transport entity by its UUID.
     *
     * @param id The UUID of the transport entity to find.
     * @return The transport entity if found, or {@code null} if no such entity exists.
     */
    @Override
    public Transport findById(UUID id) {
        return transports.stream()
                .filter(transport -> transport.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Returns an unmodifiable list of all transport entities in the repository.
     *
     * @return An unmodifiable list of {@link Transport} objects.
     */
    @Override
    public List<Transport> getAll() {
        return Collections.unmodifiableList(transports);
    }

    /**
     * Loads the list of transport entities from the JSON file into the repository's internal list.
     *
     * @return A list of {@link Transport} entities. Returns an empty list if there are errors during file reading.
     */
    @Override
    public List<Transport> load() {
        try {
            return jsonSerializer.read(Transport.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Saves the current state of the transport list to the JSON file.
     *
     * @throws IOException if an I/O error occurs during writing to the file.
     */
    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(transports);
    }
}
