package com.pdp.telegram.repository.telegramDeliverer;

import com.pdp.json.serializer.JsonSerializer;
import com.pdp.telegram.model.telegramDeliverer.TelegramDeliverer;
import com.pdp.web.repository.BaseRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * Date: 14/May/2024  14:39
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramDelivererRepository implements BaseRepository<TelegramDeliverer, List<TelegramDeliverer>> {
    private static volatile TelegramDelivererRepository instance;
    private static JsonSerializer<TelegramDeliverer> jsonSerializer;

    public static TelegramDelivererRepository getInstance() {
        if (instance == null) {
            synchronized (TelegramDelivererRepository.class) {
                if (instance == null) {
                    instance = new TelegramDelivererRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_TELEGRAM_DELIVERER));
                }
            }
        }
        return instance;
    }

    @Override
    public boolean add(@NonNull TelegramDeliverer object) {
        List<TelegramDeliverer> load = load();
        load.add(object);
        save(load);
        return true;
    }

    @Override
    public boolean remove(@NonNull UUID id) {
        List<TelegramDeliverer> load = load();
        boolean b = load.removeIf(t -> Objects.equals(t.getId(), id));
        if (b) save(load);
        return b;
    }

    @Override
    public TelegramDeliverer findById(@NonNull UUID id) {
        return load().stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<TelegramDeliverer> getAll() {
        return load();
    }

    @SneakyThrows
    @Override
    public List<TelegramDeliverer> load() {
        return jsonSerializer.read(TelegramDeliverer.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<TelegramDeliverer> telegramDeliverers) {
        jsonSerializer.write(telegramDeliverers);
    }
}
