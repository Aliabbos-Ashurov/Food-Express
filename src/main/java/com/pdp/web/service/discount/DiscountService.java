package com.pdp.web.service.discount;

import com.pdp.web.model.discount.Discount;
import com.pdp.web.repository.discount.DiscountRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

public interface DiscountService extends BaseService<Discount, List<Discount>> {
    DiscountRepository repository = DiscountRepository.getInstance();
}
