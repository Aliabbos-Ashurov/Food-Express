package com.pdp.backend.web.service.discount;

import com.pdp.backend.web.model.discount.Discount;
import com.pdp.backend.web.repository.discount.DiscountRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

public interface DiscountService extends BaseService<Discount, List<Discount>> {
    DiscountRepository repository = DiscountRepository.getInstance();
}
