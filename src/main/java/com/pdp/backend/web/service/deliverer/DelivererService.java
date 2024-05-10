package com.pdp.backend.web.service.deliverer;

import com.pdp.backend.web.model.deliverer.Deliverer;
import com.pdp.backend.web.repository.deliverer.DelivererRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

public interface DelivererService extends BaseService<Deliverer, List<Deliverer>> {
    DelivererRepository repository = DelivererRepository.getInstance();
}
