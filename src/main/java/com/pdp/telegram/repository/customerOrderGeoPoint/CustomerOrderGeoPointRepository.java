package com.pdp.telegram.repository.customerOrderGeoPoint;

import com.pdp.json.serializer.JsonSerializer;
import com.pdp.telegram.model.customerOrderGeoPoint.CustomerOrderGeoPoint;
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
 * Date: 14/May/2024  14:37
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerOrderGeoPointRepository implements BaseRepository<CustomerOrderGeoPoint, List<CustomerOrderGeoPoint>> {
    private static volatile CustomerOrderGeoPointRepository instance;
    private static JsonSerializer<CustomerOrderGeoPoint> jsonSerializer;

    public static CustomerOrderGeoPointRepository getInstance() {
        if (instance == null) {
            synchronized (CustomerOrderGeoPointRepository.class) {
                if (instance == null) {
                    instance = new CustomerOrderGeoPointRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_TELEGRAM_CUSTOMER_ORDER_GEO_POINT));
                }
            }
        }
        return instance;
    }

    @Override
    public boolean add(@NonNull CustomerOrderGeoPoint object) {
        List<CustomerOrderGeoPoint> load = load();
        load.add(object);
        save(load);
        return true;
    }

    @Override
    public boolean remove(@NonNull UUID id) {
        List<CustomerOrderGeoPoint> load = load();
        boolean b = load.removeIf(c -> Objects.equals(c.getId(), id));
        if (b) save(load);
        return b;
    }

    @Override
    public CustomerOrderGeoPoint findById(@NonNull UUID id) {
        return load().stream()
                .filter(c -> Objects.equals(c.getId(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<CustomerOrderGeoPoint> getAll() {
        return load();
    }

    @SneakyThrows
    @Override
    public List<CustomerOrderGeoPoint> load() {
        return jsonSerializer.read(CustomerOrderGeoPoint.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<CustomerOrderGeoPoint> customerOrderGeoPoints) {
        jsonSerializer.write(customerOrderGeoPoints);
    }
}
