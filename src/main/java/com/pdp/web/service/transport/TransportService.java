package com.pdp.web.service.transport;

import com.pdp.web.model.transport.Transport;
import com.pdp.web.repository.transport.TransportRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

public interface TransportService extends BaseService<Transport, List<Transport>> {
    TransportRepository repository = TransportRepository.getInstance();
}
