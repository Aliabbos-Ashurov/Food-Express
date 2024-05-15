package com.pdp.telegram.service.telegramTransport;

import com.pdp.telegram.model.telegramTransport.TelegramTransport;
import com.pdp.telegram.repository.telegramTransport.TelegramTransportRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * This interface defines the service layer for managing Telegram transports.
 * It extends the BaseService interface.
 *
 * @param <TelegramTransport>       The type representing a Telegram transport.
 * @param <List<TelegramTransport>> The type representing a list of Telegram transports.
 * @author Doniyor Nishonov
 * @since 14th May 2024, 15:14
 */
public interface TelegramTransportService extends BaseService<TelegramTransport, List<TelegramTransport>> {
    TelegramTransportRepository repository = TelegramTransportRepository.getInstance();
}
