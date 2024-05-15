package com.pdp.telegram.service.telegramTransport;

import com.pdp.telegram.model.telegramTransport.TelegramTransport;
import com.pdp.utils.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  15:15
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramTransportServiceImp implements TelegramTransportService {
    private static volatile TelegramTransportServiceImp instance;

    public static TelegramTransportServiceImp getInstance() {
        if (instance == null) {
            synchronized (TelegramTransportServiceImp.class) {
                if (instance == null) {
                    instance = new TelegramTransportServiceImp();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean add(@NonNull TelegramTransport telegramTransport) {
        return repository.add(telegramTransport);
    }

    @Override
    public boolean remove(@NonNull UUID id) {
        return repository.remove(id);
    }

    @Override
    public boolean update(@NonNull TelegramTransport object) {
        return false;
    }

    @Override
    public List<TelegramTransport> search(@NonNull String query) {
        return getAll().stream()
                .filter(t -> Validator.isValid(t.getDisplayName(), query))
                .toList();
    }

    @Override
    public TelegramTransport getByID(@NonNull UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<TelegramTransport> getAll() {
        return repository.getAll();
    }
}
