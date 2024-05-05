package com.pdp.backend.web.repository.user;

import com.pdp.backend.web.config.path.ResoucePath;
import com.pdp.backend.web.model.user.User;
import com.pdp.backend.web.repository.BaseRepository;
import com.pdp.json.serializer.JsonSerializer;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Aliabbos Ashurov
 * @since 04/May/2024  17:06
 **/
public class UserRepository implements BaseRepository<User> {
    private final JsonSerializer<User> jsonSerializer;
    private final List<User> users;

    public UserRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(ResoucePath.USER));
        this.users = load();
    }

    @Override
    public boolean add(User user) {
        users.add(user);
        save();
        return true;
    }

    @Override
    public boolean remove(UUID id) {
        boolean removed = users.removeIf(user -> user.getId().equals(id));
        if (removed) save();
        return removed;
    }

    @Override
    public User findById(UUID id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<User> getAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public List<User> load() {
        try {
            return jsonSerializer.read(User.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(users);
    }
}
