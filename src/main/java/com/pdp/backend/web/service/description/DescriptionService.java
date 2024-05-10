package com.pdp.backend.web.service.description;

import com.pdp.backend.web.model.description.Description;
import com.pdp.backend.web.repository.description.DescriptionRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

public interface DescriptionService extends BaseService<Description, List<Description>> {
    DescriptionRepository repository = DescriptionRepository.getInstance();
}
