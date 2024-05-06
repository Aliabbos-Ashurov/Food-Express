package com.pdp.backend.web.repository.banned;

import com.pdp.backend.web.model.banned.Banned;
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
 * Manages the storage and retrieval of {@link Banned} entities, providing a way to
 * record and access the details of banned users. Utilizes a {@link JsonSerializer}
 * to handle serialization to and from JSON for persistence.
 * <p>
 * Implements the {@link BaseRepository} interface, offering basic CRUD
 * operations such as add, remove, findById, and getAll.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 15:43
 */
public class BannedRepository implements BaseRepository<Banned,List<Banned>> {
    private final JsonSerializer<Banned> jsonSerializer;
    private final List<Banned> banneds;

    public BannedRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(PATH_BANNED));
        this.banneds = load();
    }

    @Override
    public boolean add(Banned banned) {
        banneds.add(banned);
        save();
        return true;
    }

    @Override
    public boolean remove(UUID id) {
        boolean removed = banneds.removeIf((banned -> banned.getId().equals(id)));
        if (removed) save();
        return removed;
    }

    /**
     * Retrieves a banned entity based on its unique identifier.
     *
     * @param id The UUID of the banned entity to find.
     * @return The {@link Banned} entity if found; null otherwise.
     */
    @Override
    public Banned findById(UUID id) {
        return banneds.stream()
                .filter((banned -> banned.getId().equals(id)))
                .findFirst().orElse(null);
    }

    /**
     * Gets an unmodifiable list of all banned entities in the repository.
     *
     * @return An unmodifiable list representing all the banned entities.
     */
    @Override
    public List<Banned> getAll() {
        return Collections.unmodifiableList(banneds);
    }

    @Override
    public List<Banned> load() {
        try {
            return jsonSerializer.read(Banned.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(banneds);
    }
}
