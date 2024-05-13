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
 * The {@code BrandRepository} class handles the persistence of {@link Brand} entities, offering CRUD operations
 * for managing a collection of brand records. It uses {@link JsonSerializer} to serialize and deserialize
 * {@code Brand} instances to and from a designated JSON file.
 * <p>
 * By implementing the {@link BaseRepository} interface, this class provides a consistent set of methods
 * for interacting with brand data. The repository maintains an in-memory list of {@code Brand} objects,
 * ensuring high-performance access, and synchronizes this list with a JSON storage file defined by
 * {@code PATH_BRAND}.
 * <p>
 * All operations performed on the repository are immediately persisted to the JSON storage to ensure data integrity
 * and seamless recovery on subsequent application runs.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 15:51
 */
public class BrandRepository implements BaseRepository<Brand, List<Brand>> {
    private static volatile BrandRepository instance;
    private static JsonSerializer<Brand> jsonSerializer;

    private BrandRepository() {
    }

    /**
     * Lazily loads and returns a singleton instance of {@code BrandRepository}, ensuring
     * thread-safe instantiation. The Json storage path is initialized following the singleton
     * creation.
     *
     * @return The single active {@code BrandRepository} instance.
     */
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

    /**
     * Synchronously reads the entire list of brand data from persistent JSON storage,
     * deserializing it into a list of {@code Brand} objects.
     *
     * @return A {@link List} of {@code Brand} objects.
     * @throws Exception If any I/O errors occur during reading from storage.
     */
    @SneakyThrows
    @Override
    public List<Brand> load() {
        return jsonSerializer.read(Brand.class);
    }

    /**
     * Synchronously writes the current state of the brand list to persistent JSON storage,
     * serializing it from the in-memory {@link List} representation.
     *
     * @param list The {@link List} of {@code Brand} objects to write to storage.
     * @throws Exception If any I/O errors occur during writing to storage.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<Brand> list) {
        jsonSerializer.write(list);
    }
}
