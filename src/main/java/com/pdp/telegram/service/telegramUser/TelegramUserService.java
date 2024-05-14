package com.pdp.telegram.service.telegramUser;

import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.telegram.repository.telegramUser.TelegramUserRepository;
import com.pdp.web.enums.Language;
import com.pdp.web.service.BaseService;
import lombok.NonNull;

import java.util.List;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  15:16
 **/
public interface TelegramUserService extends BaseService<TelegramUser, List<TelegramUser>> {
    TelegramUserRepository repository = TelegramUserRepository.getInstance();

    Language findLanguageByChatID(@NonNull Long chatID);
}
