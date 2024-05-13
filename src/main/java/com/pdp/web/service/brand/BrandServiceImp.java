package com.pdp.web.service.brand;

import com.pdp.utils.Validator;
import com.pdp.web.model.brand.Brand;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Singleton service class for managing brand operations.
 * This class implements {@link BrandService} interface.
 *
 * @see BrandService
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrandServiceImp implements BrandService {
    private static volatile BrandServiceImp instance;

    /**
     * Retrieves the singleton instance of BrandServiceImp.
     *
     * @return The singleton instance of BrandServiceImp.
     */
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
     * Adds a new brand if it does not already exist.
     *
     * @return True if the brand was added successfully, false if it already exists.
     */
    @Override
    public boolean add(@NonNull Brand object) {
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
     * @return True if the brand was removed successfully, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing brand with new information.
     *
     * @return True if the brand was updated successfully, false otherwise.
     */
    @Override
    public boolean update(@NonNull Brand brand) {
        List<Brand> brands = getAll();
        Optional<Brand> brandOptional = brands.stream()
                .filter(o -> o.getId().equals(brand.getId()))
                .findFirst();
        if (brandOptional.isPresent()) {
            updateBrandData(brandOptional.get(), brand);
            repository.save(brands);
            return true;
        }
        return false;
    }

    private void updateBrandData(@NonNull Brand current, Brand updated) {
        current.setOpeningTime(updated.getOpeningTime());
        current.setClosingTime(updated.getClosingTime());
        current.setImageURL(updated.getImageURL());
        current.setDescriptionID(updated.getDescriptionID());
    }

    /**
     * Searches for brands that match the given query.
     *
     * @param query The search query.
     * @return A list of brands that match the query.
     */
    @Override
    public List<Brand> search(@NonNull String query) {
        List<Brand> brands = getAll();
        return brands.stream()
                .filter(brand -> Validator.isValid(brand.getName(), query))
                .toList();
    }

    /**
     * Retrieves the brand with the provided UUID.
     *
     * @param id The UUID of the brand to retrieve.
     * @return The matching brand, or null if not found.
     */
    @Override
    public Brand getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all brands.
     *
     * @return A list of all brands.
     */
    @Override
    public List<Brand> getAll() {
        return repository.getAll();
    }
}
