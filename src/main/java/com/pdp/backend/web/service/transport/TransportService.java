package com.pdp.backend.web.service.transport;

import com.pdp.backend.web.model.transport.Transport;
import com.pdp.backend.web.repository.transport.TransportRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

public interface TransportService extends BaseService<Transport, List<Transport>> {
    TransportRepository repository = TransportRepository.getInstance();
}
