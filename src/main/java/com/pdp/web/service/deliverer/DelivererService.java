package com.pdp.web.service.deliverer;

import com.pdp.web.model.deliverer.Deliverer;
import com.pdp.web.repository.deliverer.DelivererRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

public interface DelivererService extends BaseService<Deliverer, List<Deliverer>> {
    DelivererRepository repository = DelivererRepository.getInstance();
}
