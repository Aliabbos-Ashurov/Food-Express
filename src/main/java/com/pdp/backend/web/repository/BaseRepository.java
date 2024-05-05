package com.pdp.backend.web.repository;

import java.util.List;
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
public interface BaseRepository<T> {

    /**
     * Adds a new entity to the repository.
     *
     * @param object The entity to add.
     * @return True if the entity is successfully added, false otherwise.
     */
    boolean add(T object);

    /**
     * Removes an entity from the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to remove.
     * @return True if the entity is successfully removed, false otherwise.
     */
    boolean remove(UUID id);

    /**
     * Retrieves an entity from the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to retrieve.
     * @return The entity if found, null otherwise.
     */
    T findById(UUID id);

    /**
     * Retrieves all entities from the repository.
     *
     * @return A list of all entities.
     */
    List<T> getAll();

    List<T> load();
    void save();
}
