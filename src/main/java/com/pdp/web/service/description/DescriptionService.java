package com.pdp.web.service.description;

import com.pdp.web.model.description.Description;
import com.pdp.web.repository.description.DescriptionRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

public interface DescriptionService extends BaseService<Description, List<Description>> {
    DescriptionRepository repository = DescriptionRepository.getInstance();
}
