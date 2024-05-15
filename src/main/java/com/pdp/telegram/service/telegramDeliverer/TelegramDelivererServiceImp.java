package com.pdp.telegram.service.telegramDeliverer;

import com.pdp.telegram.model.telegramDeliverer.TelegramDeliverer;
import com.pdp.utils.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  15:13
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramDelivererServiceImp implements TelegramDelivererService {
    private static volatile TelegramDelivererServiceImp instance;

    public static TelegramDelivererServiceImp getInstance() {
        if (instance == null) {
            synchronized (TelegramDelivererServiceImp.class) {
                if (instance == null) {
                    instance = new TelegramDelivererServiceImp();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean add(@NonNull TelegramDeliverer telegramDeliverer) {
        List<TelegramDeliverer> telegramDeliverers = getAll();
        boolean anyMatch = telegramDeliverers.stream().anyMatch(t -> Objects.equals(t.getTelegramUserID(), telegramDeliverer.getTelegramUserID()));
        if (!anyMatch) return false;
        repository.add(telegramDeliverer);
        return true;
    }

    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    @Override
    public boolean update(@NonNull TelegramDeliverer telegramDeliverer) {
        List<TelegramDeliverer> telegramDeliverers = getAll();
        Optional<TelegramDeliverer> first = telegramDeliverers.stream()
                .filter(t -> Objects.equals(t.getId(), telegramDeliverer.getId()))
                .findFirst();
        if (first.isPresent()) {
            updateTelegramDelivererData(first.get(), telegramDeliverer);
            repository.save(telegramDeliverers);
            return true;
        }
        return false;
    }

    private void updateTelegramDelivererData(TelegramDeliverer deliverer, TelegramDeliverer updated) {
        deliverer.setFullname(updated.getFullname());
        deliverer.setTelegramUserID(updated.getTelegramUserID());
    }

    @Override
    public List<TelegramDeliverer> search(@NonNull String query) {
        return getAll().stream()
                .filter(t -> Validator.isValid(t.getDisplayName(), query))
                .toList();
    }

    @Override
    public TelegramDeliverer getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<TelegramDeliverer> getAll() {
        return repository.getAll();
    }
}
