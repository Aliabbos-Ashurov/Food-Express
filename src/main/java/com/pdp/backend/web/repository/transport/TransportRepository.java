package com.pdp.backend.web.repository.transport;

import com.pdp.backend.web.config.path.ResoucePath;
import com.pdp.backend.web.model.transport.Transport;
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
 * @since 04/May/2024  17:03
 **/
public class TransportRepository implements BaseRepository<Transport> {
    private final JsonSerializer<Transport> jsonSerializer;
    private final List<Transport> transports;

    public TransportRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(ResoucePath.TRANSPORT));
        this.transports = load();
    }

    @Override
    public boolean add(Transport transport) {
        transports.add(transport);
        save();
        return true;
    }

    @Override
    public boolean remove(UUID id) {
        boolean removed = transports.removeIf(transport -> transport.getId().equals(id));
        if (removed) save();
        return removed;
    }

    @Override
    public Transport findById(UUID id) {
        return transports.stream()
                .filter(transport -> transport.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Transport> getAll() {
        return Collections.unmodifiableList(transports);
    }

    @Override
    public List<Transport> load() {
        try {
            return jsonSerializer.read(Transport.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(transports);
    }
}
