package com.pdp.web.service.category;

import com.pdp.web.model.category.Category;
import com.pdp.web.repository.category.CategoryRepository;
import com.pdp.web.service.BaseService;

import java.util.Set;
import java.util.UUID;

/**
 * Interface for services related to handling category operations.
 * This service extends the generic {@link BaseService} interface for common CRUD operations
 * tailored to the {@link Category} model.
 *
 * <p>Through this interface, additional behavior tied to the {@link Category} entity
 * is exposed, such as retrieving categories linked to a specific brand.</p>
 *
 * @see BaseService
 * @see Category
 * @see CategoryRepository
 */

public interface CategoryService extends BaseService<Category, Set<Category>> {
    CategoryRepository repository = CategoryRepository.getInstance();

    /**
     * Retrieves a set of categories associated with a specific brand, identified by its UUID.
     *
     * @param brandID The UUID of the brand whose categories are to be retrieved.
     * @return A set of {@link Category} instances associated with the given brand.
     */
    Set<Category> getBrandCategories(UUID brandID);
}
