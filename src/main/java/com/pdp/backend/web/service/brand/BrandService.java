package com.pdp.backend.web.service.brand;

import com.pdp.backend.web.model.brand.Brand;
import com.pdp.backend.web.repository.brand.BrandRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

/**
 * Interface for services relating to brand operations.
 * This extends BaseService to provide common CRUD operations specific to Brand entities.
 */
public interface BrandService extends BaseService<Brand, List<Brand>> {
    BrandRepository repository = new BrandRepository();
}
