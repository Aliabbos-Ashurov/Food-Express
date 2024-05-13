package com.pdp.web.service.brand;

import com.pdp.web.model.brand.Brand;
import com.pdp.web.repository.brand.BrandRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * Defines the contract for a service handling Brand-related operations.
 * This interface specifies methods necessary for managing the lifecycle of Brand entities
 * and extends {@link BaseService}, adapting its operations for the Brand context.
 * <p>
 * Operations include standard CRUD methods tailored to Brand entities:
 * - Addition of a new Brand
 * - Retrieval of one or multiple Brands
 * - Update of an existing Brand
 * - Deletion of a Brand
 * </p>
 *
 * @see BaseService The generic service interface this interface extends from.
 * @see Brand The entity type this service manages.
 * @see BrandRepository The repository interface handling Brand persistence operations.
 */
public interface BrandService extends BaseService<Brand, List<Brand>> {
    BrandRepository repository = BrandRepository.getInstance();
}
