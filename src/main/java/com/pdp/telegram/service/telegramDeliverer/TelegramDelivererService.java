package com.pdp.telegram.service.telegramDeliverer;

import com.pdp.telegram.model.telegramDeliverer.TelegramDeliverer;
import com.pdp.telegram.repository.telegramDeliverer.TelegramDelivererRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * This interface defines the service layer for managing Telegram deliverers.
 * It extends the BaseService interface.
 *
 * @param <TelegramDeliverer> The type representing a Telegram deliverer.
 * @param <List<TelegramDeliverer>> The type representing a list of Telegram deliverers.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 15:13
 */
public interface TelegramDelivererService extends BaseService<TelegramDeliverer, List<TelegramDeliverer>> {
    TelegramDelivererRepository repository = TelegramDelivererRepository.getInstance();
}
