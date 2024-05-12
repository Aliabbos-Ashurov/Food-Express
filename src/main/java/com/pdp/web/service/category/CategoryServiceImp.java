package com.pdp.web.service.category;

import com.pdp.utils.Validator;
import com.pdp.web.model.category.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the CategoryService for managing category entities.
 * Uses the Singleton design pattern to provide a global access point to the service.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryServiceImp implements CategoryService {
    private static volatile CategoryServiceImp instance;

    public static CategoryServiceImp getInstance() {
        if (instance == null) {
            synchronized (CategoryServiceImp.class) {
                if (instance == null) {
                    instance = new CategoryServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Retrieves a list of categories associated with a specific brand.
     *
     * @param brandID UUID of the brand to filter categories by.
     * @return A List of Category entities associated with the specified brand.
     */
    @Override
    public Set<Category> getBrandCategories(UUID brandID) {
        Set<Category> categories = getAll();
        return categories.stream()
                .filter(category -> category.getBrandId().equals(brandID))
                .collect(Collectors.toSet());
    }

    /**
     * Adds a new category.
     *
     * @param category The Category object to add.
     * @return true if the category was successfully added; false otherwise.
     */
    @Override
    public boolean add(Category category) {
        return repository.add(category);
    }

    /**
     * Removes a category by its UUID.
     *
     * @param id UUID of the category to remove.
     * @return true if the category was successfully removed; false otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    @Override
    public boolean update(Category object) {
        return false;
    }

    /**
     * Searches for categories in the repository that match a given query string.
     *
     * @param query the string to search for within category names.
     * @return a set of category entities that match the query string.
     */
    @Override
    public Set<Category> search(String query) {
        Set<Category> categories = getAll();
        return categories.stream()
                .filter(category -> Validator.isValid(category.getName(), query))
                .collect(Collectors.toSet());
    }

    /**
     * Retrieves a category by its UUID.
     *
     * @param id the unique identifier of the category to retrieve.
     * @return the corresponding Category entity if found; otherwise, {@code null}.
     */
    @Override
    public Category getByID(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Set<Category> getAll() {
        return repository.getAll();
    }
}
