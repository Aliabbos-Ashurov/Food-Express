package com.pdp.backend.web.service.category;

import com.pdp.backend.web.model.category.Category;
import com.pdp.backend.web.repository.category.CategoryRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.Set;
import java.util.UUID;

public interface CategoryService extends BaseService<Category, Set<Category>> {
    CategoryRepository repository = new CategoryRepository();
}
