package com.pdp.backend.web.service.brand;

import com.pdp.backend.utils.Validator;
import com.pdp.backend.web.model.brand.Brand;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Singleton service class for managing brand operations.
 * @see BrandService
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrandServiceImp implements BrandService {
    private static volatile BrandServiceImp instance;

    public static BrandServiceImp getInstance() {
        if (instance == null) {
            synchronized (BrandServiceImp.class) {
                if (instance == null) {
                    instance = new BrandServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Adds a new brand, ensuring it does not already exist.
     *
     * @param object The brand object to add.
     * @return True if the brand was added, false if it already exists.
     */
    @Override
    public boolean add(Brand object) {
        List<Brand> brands = getAll();
        boolean match = brands.stream()
                .anyMatch(brand -> brand.getName().equals(object.getName()));
        if (!match) repository.add(object);
        return !match;
    }

    /**
     * Removes the brand with the specified UUID.
     *
     * @param id The UUID of the brand to be removed.
     * @return True if the brand was removed, false otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing brand with new information. Currently not implemented.
     *
     * @param brand The brand object to update.
     * @return False as this operation is not supported.
     */
    @Override
    public boolean update(Brand brand) {
        return false;
    }

    /**
     * Searches for all brands that match the given query.
     *
     * @param query The query to match against brand names.
     * @return A list of brands that match the query.
     */
    @Override
    public List<Brand> search(String query) {
        List<Brand> brands = getAll();
        return brands.stream()
                .filter(brand -> Validator.isValid(brand.getName(),query))
                .toList();
    }

    /**
     * Retrieves the brand with the provided UUID.
     *
     * @param id The UUID of the brand to retrieve.
     * @return The matching brand, or null if not found.
     */
    @Override
    public Brand getByID(UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all brands.
     *
     * @return A list of all brands.
     */
    @Override
    public List<Brand> getAll() {
        return getAll();
    }
}
