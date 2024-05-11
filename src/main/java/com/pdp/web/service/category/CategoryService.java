package com.pdp.web.service.category;

import com.pdp.web.model.category.Category;
import com.pdp.web.repository.category.CategoryRepository;
import com.pdp.web.service.BaseService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Interface for services related to handling category operations.
 * This service extends the generic BaseService interface for common CRUD operations
 * tailored to the Category model.
 *
 * @see BaseService
 * @see Category
 */
public interface CategoryService extends BaseService<Category, Set<Category>> {
    CategoryRepository repository = CategoryRepository.getInstance();

    /**
     * Retrieves a list of categories associated with a particular brand, identified by UUID.
     *
     * @param brandID The UUID of the brand whose categories are to be retrieved.
     * @return A list of {@link Category} instances associated with the given brand.
     */
    List<Category> getBrandCategories(UUID brandID);
}
