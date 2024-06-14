package com.pdp.web.service.discount;

import com.pdp.web.model.discount.Discount;
import com.pdp.web.repository.discount.DiscountRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * Service interface for managing discounts.
 * <p>
 * This interface defines methods for adding, removing, updating, searching,
 * and retrieving discounts within the system.
 * </p>
 * <p>
 * Implementing classes should provide functionality to interact with a repository
 * storing discounts, typically through a {@link DiscountRepository}.
 * </p>
 * @author Nishonov Doniyor
 * @see DiscountRepository For managing the storage of discounts.
 * @see BaseService Base service interface providing common methods for service implementations.
 */
public interface DiscountService extends BaseService<Discount, List<Discount>> {
    DiscountRepository repository = new DiscountRepository();
}
