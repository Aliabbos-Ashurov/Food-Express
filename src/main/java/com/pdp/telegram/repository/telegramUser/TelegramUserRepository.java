package com.pdp.telegram.repository.telegramUser;

import com.pdp.config.jsonFilePath.JsonFilePath;
import com.pdp.json.serializer.JsonSerializer;
import com.pdp.telegram.model.telegramUser.TelegramUser;
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
 * Date: 14/May/2024  14:43
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramUserRepository implements BaseRepository<TelegramUser, List<TelegramUser>> {
    private static volatile TelegramUserRepository instance;
    private static JsonSerializer<TelegramUser> jsonSerializer;

    public static TelegramUserRepository getInstance() {
        if (instance == null) {
            synchronized (TelegramUserRepository.class) {
                if (instance == null) {
                    instance = new TelegramUserRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_TELEGRAM_USER));
                }
            }
        }
        return instance;
    }

    @Override
    public boolean add(@NonNull TelegramUser object) {
        List<TelegramUser> load = load();
        load.add(object);
        save(load);
        return true;
    }

    @Override
    public boolean remove(@NonNull UUID id) {
        List<TelegramUser> load = load();
        boolean b = load.removeIf(t -> Objects.equals(t.getId(), id));
        if (b) save(load);
        return b;
    }

    @Override
    public TelegramUser findById(@NonNull UUID id) {
        return load().stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<TelegramUser> getAll() {
        return load();
    }

    @SneakyThrows
    @Override
    public List<TelegramUser> load() {
        return jsonSerializer.read(TelegramUser.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<TelegramUser> telegramUsers) {
        jsonSerializer.write(telegramUsers);
    }
}
