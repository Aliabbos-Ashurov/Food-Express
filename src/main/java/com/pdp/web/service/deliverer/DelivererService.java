package com.pdp.web.service.deliverer;

import com.pdp.web.model.deliverer.Deliverer;
import com.pdp.web.repository.deliverer.DelivererRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * Service interface responsible for managing operations related to deliverers.
 * This interface extends the BaseService to provide CRUD operations for Deliverer entities.
 *
 * @see BaseService
 * @see Deliverer
 */
public interface DelivererService extends BaseService<Deliverer, List<Deliverer>> {
    DelivererRepository repository = DelivererRepository.getInstance();
}
