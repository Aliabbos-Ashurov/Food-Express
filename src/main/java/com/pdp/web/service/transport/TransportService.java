package com.pdp.web.service.transport;

import com.pdp.web.model.transport.Transport;
import com.pdp.web.repository.transport.TransportRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * Interface for services related to managing transport entities.
 * This service provides basic CRUD operations for transport entities.
 *
 * @see Transport
 * @see TransportRepository
 */
public interface TransportService extends BaseService<Transport, List<Transport>> {
    /**
     * The singleton instance of the TransportRepository.
     */
    TransportRepository repository = TransportRepository.getInstance();
}
