package com.pdp.backend.web.service;

import com.pdp.backend.web.model.BaseModel;

import java.util.Collection;
import java.util.UUID;

/**
 * The base interface defining the contract for services operating on entities that extend {@link BaseModel}.
 * It includes common operations such as add, remove, update, and retrieval of entities either individually
 * by their ID or collectively as a part of a collection.
 *
 * @param <T> the type parameter extending {@link BaseModel}, representing the entity model
 * @param <R> the type parameter extending {@link Collection}, representing the collection of entity models
 * @author Aliabbos Ashurov
 * @since 06/May/2024 11:56
 */
public interface BaseService<T extends BaseModel,R extends Collection<T>> {

    /**
     * Adds a new entity to the service.
     *
     * @param object the entity object to add
     * @return true if the entity is successfully added, false otherwise
     */
    boolean add(T object);

    /**
     * Removes an entity from the service by its unique identifier.
     *
     * @param id the unique identifier of the entity to remove
     * @return true if the entity is successfully removed, false otherwise
     */
    boolean remove(UUID id);

    /**
     * Updates an existing entity within the service.
     *
     * @param object the entity object with updated information
     * @return true if the entity is successfully updated, false otherwise
     */
    boolean update(T object);

    /**
     * Searches for entities that match the given query string.
     *
     * @param query the search query string
     * @return a collection of entities that match the search criteria
     */
    R search(String query);

    /**
     * Retrieves a single entity by its unique identifier.
     *
     * @param id the unique identifier of the entity to retrieve
     * @return the entity with the specified ID or null if it does not exist
     */
    T getByID(UUID id);

    /**
     * Gets all entities managed by the service.
     *
     * @return a collection of all entities
     */
    R getAll();
}
