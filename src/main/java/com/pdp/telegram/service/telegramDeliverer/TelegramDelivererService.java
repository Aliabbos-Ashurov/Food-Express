package com.pdp.telegram.service.telegramDeliverer;

import com.pdp.telegram.model.telegramDeliverer.TelegramDeliverer;
import com.pdp.telegram.repository.telegramDeliverer.TelegramDelivererRepository;
import com.pdp.web.service.BaseService;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * This interface defines the service layer for managing Telegram deliverers.
 * It extends the BaseService interface.
 *
 * @author Doniyor Nishonov
 * @since 14th May 2024, 15:13
 */
public interface TelegramDelivererService extends BaseService<TelegramDeliverer, List<TelegramDeliverer>> {
    TelegramDelivererRepository repository = TelegramDelivererRepository.getInstance();

    TelegramDeliverer getDeliverByTelegramId(@NonNull UUID telegramUserID);
}
