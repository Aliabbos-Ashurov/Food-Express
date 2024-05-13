package com.pdp.web.service.category;

import com.pdp.utils.Validator;
import com.pdp.web.model.category.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link CategoryService} interface for managing category entities.
 * This class follows the Singleton design pattern to ensure a single global instance of the service.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryServiceImp implements CategoryService {
    private static volatile CategoryServiceImp instance;

    /**
     * Retrieves the singleton instance of {@link CategoryServiceImp}.
     *
     * @return The singleton instance of {@link CategoryServiceImp}.
     */
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
     * Retrieves a set of categories associated with a specific brand UUID.
     *
     * @param brandID The UUID of the brand to filter categories by.
     * @return A set of {@link Category} instances associated with the specified brand UUID.
     */
    @Override
    public Set<Category> getBrandCategories(UUID brandID) {
        Set<Category> categories = getAll();
        return categories.stream()
                .filter(category -> category.getBrandId().equals(brandID))
                .collect(Collectors.toSet());
    }

    /**
     * Adds a new category to the repository.
     *
     * @param category The {@link Category} object to add.
     * @return {@code true} if the category was successfully added; {@code false} otherwise.
     */
    @Override
    public boolean add(@NonNull Category category) {
        return repository.add(category);
    }

    /**
     * Removes a category from the repository by its UUID.
     *
     * @param id The UUID of the category to remove.
     * @return {@code true} if the category was successfully removed; {@code false} otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates a category in the repository. This method is not implemented in the current class.
     *
     * @param object The {@link Category} object representing the updated category information.
     * @return Always returns {@code false} as the update operation is not supported.
     */
    @Override
    public boolean update(@NonNull Category category) {
        Set<Category> categories = getAll();
        Optional<Category> optional = categories.stream()
                .filter(o -> o.getId().equals(category.getId()))
                .findFirst();
        if (optional.isPresent()) {
            updateCategoryData(optional.get(), category);
            repository.save(categories);
            return true;
        }
        return false;
    }

    private void updateCategoryData(Category current, Category updated) {
        current.setBrandId(updated.getBrandId());
        current.setName(updated.getName());
        current.setImageUrl(updated.getImageUrl());
    }

    /**
     * Searches for categories in the repository that match a given query string.
     *
     * @param query the string to search for within category names.
     * @return a set of category entities that match the query string.
     */
    @Override
    public Set<Category> search(@NonNull String query) {
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
    public Category getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all categories from the repository.
     *
     * @return A set of all category entities.
     */
    @Override
    public Set<Category> getAll() {
        return repository.getAll();
    }
}
