package com.pdp.telegram.repository.telegramTransport;

import com.pdp.json.serializer.JsonSerializer;
import com.pdp.telegram.model.telegramTransport.TelegramTransport;
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
 * Date: 14/May/2024  14:42
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramTransportRepository implements BaseRepository<TelegramTransport, List<TelegramTransport>> {
    private static volatile TelegramTransportRepository instance;
    private static JsonSerializer<TelegramTransport> jsonSerializer;

    public static TelegramTransportRepository getInstance() {
        if (instance == null) {
            synchronized (TelegramTransportRepository.class) {
                if (instance == null) {
                    instance = new TelegramTransportRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_TELEGRAM_TRANSPORT));
                }
            }
        }
        return instance;
    }

    @Override
    public boolean add(@NonNull TelegramTransport object) {
        List<TelegramTransport> load = load();
        load.add(object);
        save(load);
        return true;
    }

    @Override
    public boolean remove(@NonNull UUID id) {
        List<TelegramTransport> load = load();
        boolean b = load.removeIf(t -> Objects.equals(t.getId(), id));
        if (b) save(load);
        return b;
    }

    @Override
    public TelegramTransport findById(@NonNull UUID id) {
        return load().stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<TelegramTransport> getAll() {
        return load();
    }

    @SneakyThrows
    @Override
    public List<TelegramTransport> load() {
        return jsonSerializer.read(TelegramTransport.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<TelegramTransport> telegramTransports) {
        jsonSerializer.write(telegramTransports);
    }
}
