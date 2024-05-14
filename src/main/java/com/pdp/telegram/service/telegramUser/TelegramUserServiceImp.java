package com.pdp.telegram.service.telegramUser;

import com.pdp.telegram.model.telegramUser.TelegramUser;
import com.pdp.utils.Validator;
import com.pdp.web.enums.Language;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  15:19
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramUserServiceImp implements TelegramUserService {
    private static volatile TelegramUserServiceImp instance;

    public static TelegramUserServiceImp getInstance() {
        if (instance == null) {
            synchronized (TelegramUserServiceImp.class) {
                if (instance == null) {
                    instance = new TelegramUserServiceImp();
                }
            }
        }
        return instance;
    }

    @Override
    public Language findLanguageByChatID(@NonNull Long chatID) {
        return getAll().stream()
                .filter(t -> Objects.equals(t.getChatID(), chatID))
                .map(TelegramUser::getLanguage)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean add(@NonNull TelegramUser telegramUser) {
        boolean anyMatch = getAll().stream().anyMatch(t -> Objects.equals(t.getChatID(), telegramUser.getChatID()));
        if (anyMatch) return false;
        return repository.add(telegramUser);
    }

    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    @Override
    public boolean update(@NonNull TelegramUser telegramUser) {
        List<TelegramUser> telegramUsers = getAll();
        Optional<TelegramUser> first = telegramUsers.stream()
                .filter(t -> Objects.equals(t.getChatID(), telegramUser.getChatID()))
                .findFirst();
        if (first.isPresent()) {
            updateTelegramUserData(first.get(), telegramUser);
            return true;
        }
        return false;
    }

    private void updateTelegramUserData(TelegramUser telegramUser, TelegramUser updated) {
        telegramUser.setChatID(updated.getChatID());
        telegramUser.setLanguage(updated.getLanguage());
        telegramUser.setFirstName(updated.getFirstName());
        telegramUser.setRole(updated.getRole());
        telegramUser.setState(updated.getState());
        telegramUser.setPhoneNumber(updated.getPhoneNumber());
        telegramUser.setUsername(updated.getUsername());
    }

    @Override
    public List<TelegramUser> search(@NonNull String query) {
        return getAll().stream()
                .filter(t -> Validator.isValid(t.getDisplayName(), query))
                .toList();
    }

    @Override
    public TelegramUser getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<TelegramUser> getAll() {
        return repository.getAll();
    }
}
