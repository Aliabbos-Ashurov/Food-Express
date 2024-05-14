package com.pdp.telegram.service.telegramTransport;

import com.pdp.telegram.model.telegramTransport.TelegramTransport;
import com.pdp.telegram.repository.telegramTransport.TelegramTransportRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  15:14
 **/
public interface TelegramTransportService extends BaseService<TelegramTransport, List<TelegramTransport>> {
    TelegramTransportRepository repository = TelegramTransportRepository.getInstance();
}
