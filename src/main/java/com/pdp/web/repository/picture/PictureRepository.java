package com.pdp.web.repository.picture;

import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.picture.Picture;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * A repository class for managing the storage and retrieval of {@link Picture} entities using JSON serialization.
 * This class provides CRUD operations to interact with Picture data stored in a JSON file.
 * <p>
 * Each method ensures the synchronization of the in-memory picture list with the underlying JSON file to
 * maintain consistency between all operations and the stored data.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 16:59
 */
public class PictureRepository implements BaseRepository<Picture, List<Picture>> {
    private static JsonSerializer<Picture> jsonSerializer;
    private static volatile PictureRepository instance;

    /**
     * Constructs a new PictureRepository, setting up the JSON serializer with the file path for picture data,
     * and loading the existing pictures from this file into the memory.
     */
    private PictureRepository() {
    }

    public static PictureRepository getInstance() {
        if (instance == null) {
            synchronized (PictureRepository.class) {
                if (instance == null) {
                    instance = new PictureRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(JsonFilePath.PATH_PICTURE));
                }
            }
        }
        return instance;
    }

    /**
     * Adds a {@link Picture} to the repository and saves the updated list to the JSON file.
     *
     * @param picture The picture to add to the repository.
     * @return Always returns {@code true}, indicating that the picture was successfully added.
     */
    @Override
    public boolean add(Picture picture) {
        List<Picture> pictures = load();
        pictures.add(picture);
        save(pictures);
        return true;
    }

    /**
     * Removes a picture from the repository by its UUID and updates the JSON file if the picture is successfully removed.
     *
     * @param id The UUID of the picture to remove.
     * @return {@code true} if the picture was successfully found and removed; {@code false} otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        List<Picture> pictures = load();
        boolean removed = pictures.removeIf(picture -> picture.getId().equals(id));
        if (removed) save(pictures);
        return removed;
    }

    /**
     * Retrieves a picture by its UUID from the repository.
     *
     * @param id The UUID of the picture to find.
     * @return The picture if found, or {@code null} if no picture with such ID exists.
     */

    @Override
    public Picture findById(UUID id) {
        List<Picture> pictures = load();
        return pictures.stream()
                .filter(picture -> picture.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns an unmodifiable list of all pictures currently stored in the repository.
     *
     * @return An unmodifiable list of {@link Picture} entities.
     */
    @Override
    public List<Picture> getAll() {
        return load();
    }

    /**
     * Loads the list of pictures from the JSON file into the repository's internal list.
     *
     * @return A list of {@link Picture} entities. Returns an empty list if there is an error during the file reading.
     */
    @SneakyThrows
    @Override
    public List<Picture> load() {
        return jsonSerializer.read(Picture.class);
    }

    /**
     * Saves the current list of pictures to the JSON file.
     *
     * @throws IOException if there is an I/O error during writing to the file.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<Picture> pictures) {
        jsonSerializer.write(pictures);
    }
}
