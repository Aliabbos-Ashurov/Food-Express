package com.pdp.backend.web.service.branch;

import com.pdp.backend.web.model.branch.Branch;
import com.pdp.backend.web.repository.branch.BranchRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

public interface BranchService extends BaseService<Branch, List<Branch>> {
    BranchRepository repository = new BranchRepository();
}
