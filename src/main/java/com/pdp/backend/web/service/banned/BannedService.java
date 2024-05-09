package com.pdp.backend.web.service.banned;

import com.pdp.backend.web.model.banned.Banned;
import com.pdp.backend.web.repository.banned.BannedRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

public interface BannedService extends BaseService<Banned, List<Banned>> {
    BannedRepository repository = new BannedRepository();
}
