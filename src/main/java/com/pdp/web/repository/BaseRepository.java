package com.pdp.web.repository;

import com.pdp.config.jsonFilePath.JsonFilePath;
import com.pdp.web.model.BaseModel;
import lombok.NonNull;

import java.util.Collection;
import java.util.UUID;

/**
 * The base interface for repository operations on entities within the system.
 * This interface defines common operations such as add, remove, findById, and getAll
 * that can be implemented by various repositories handling different entity types.
 *
 * <p>By providing a generic interface, repositories can implement these operations
 * in a consistent manner while working with a specific type of entity.</p>
 *
 * @param <T> The type of the entity that the repository will manage.
 * @author Aliabbos Ashurov
 * @since 04/May/2024 14:51
 */
public interface BaseRepository<T extends BaseModel, R extends Collection<T>> extends JsonFilePath {

    /**
     * Adds a new entity to the repository.
     *
     * @param object The entity to add.
     * @return True if the entity is successfully added, false otherwise.
     */
    boolean add(@NonNull T object);

    /**
     * Removes an entity from the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to remove.
     * @return True if the entity is successfully removed, false otherwise.
     */
    boolean remove(@NonNull UUID id);

    /**
     * Retrieves an entity from the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to retrieve.
     * @return The entity if found, null otherwise.
     */
    T findById(@NonNull UUID id);

    /**
     * Retrieves all entities from the repository.
     *
     * @return A list of all entities.
     */
    R getAll();

    R load();

    void save(@NonNull R r);
}
