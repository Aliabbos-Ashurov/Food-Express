package com.pdp.telegram.service.telegramDeliverer;

import com.pdp.telegram.model.telegramDeliverer.TelegramDeliverer;
import com.pdp.telegram.repository.telegramDeliverer.TelegramDelivererRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  15:13
 **/
public interface TelegramDelivererService extends BaseService<TelegramDeliverer, List<TelegramDeliverer>> {
    TelegramDelivererRepository repository = TelegramDelivererRepository.getInstance();
}
