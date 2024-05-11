package com.pdp.web.repository.brand;

import com.pdp.web.model.brand.Brand;
import com.pdp.web.repository.BaseRepository;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * Handles the persistence of Brand entities, providing a mechanism to add, remove,
 * find, and list all Brands. This repository leverages JsonSerializer for the
 * serialization and deserialization of Brand instances to and from JSON format.
 * <p>
 * Implements the BaseRepository interface, utilizing a local in-memory list that
 * is synchronized with a JSON file defined by JsonFilePath.BRAND.
 * <p>
 * Operations to this repository are persisted to the JSON storage immediately.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 15:51
 */
public class BrandRepository implements BaseRepository<Brand, List<Brand>> {
    private static volatile BrandRepository instance;
    private static JsonSerializer<Brand> jsonSerializer;

    private BrandRepository() {
    }

    public static BrandRepository getInstance() {
        if (instance == null) {
            synchronized (BrandRepository.class) {
                if (instance == null) {
                    instance = new BrandRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_BRAND));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a Brand to the repository and persists the change.
     *
     * @param brand The Brand to be added.
     * @return true if the addition is successful, false otherwise.
     */
    @Override
    public boolean add(@NonNull Brand brand) {
        List<Brand> brands = load();
        brands.add(brand);
        save(brands);
        return true;
    }

    /**
     * Removes a Brand from the repository by its identifier and persists the change.
     *
     * @param id The UUID of the Brand to be removed.
     * @return true if the Brand is successfully removed, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<Brand> brands = load();
        boolean removed = brands.removeIf((brand -> brand.getId().equals(id)));
        if (removed) save(brands);
        return removed;
    }


    @Override
    public Brand findById(@NonNull UUID id) {
        List<Brand> brands = load();
        return brands.stream()
                .filter((brand -> brand.getId().equals(id)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Brand> getAll() {
        return load();
    }

    @SneakyThrows
    @Override
    public List<Brand> load() {
        return jsonSerializer.read(Brand.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<Brand> list) {
        jsonSerializer.write(list);
    }
}
