package com.pdp.telegram.service.telegramTransport;

import com.pdp.telegram.model.telegramTransport.TelegramTransport;
import com.pdp.telegram.repository.telegramTransport.TelegramTransportRepository;
import com.pdp.web.service.BaseService;

import java.util.List;
import java.util.UUID;

/**
 * This interface defines the service layer for managing Telegram transports.
 * It extends the BaseService interface.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 15:14
 */
public interface TelegramTransportService extends BaseService<TelegramTransport, List<TelegramTransport>> {
    TelegramTransportRepository repository = new TelegramTransportRepository();

    TelegramTransport getTransportByDeliverID(UUID telegramDeliverID);
}
