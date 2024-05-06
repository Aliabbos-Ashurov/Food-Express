package com.pdp.backend.web.repository.picture;

import com.pdp.backend.web.config.path.ResourcePath;
import com.pdp.backend.web.model.picture.Picture;
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
 * A repository class for managing the storage and retrieval of {@link Picture} entities using JSON serialization.
 * This class provides CRUD operations to interact with Picture data stored in a JSON file.
 *
 * Each method ensures the synchronization of the in-memory picture list with the underlying JSON file to
 * maintain consistency between all operations and the stored data.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 16:59
 */
public class PictureRepository implements BaseRepository<Picture> {
    private final JsonSerializer<Picture> jsonSerializer;
    private final List<Picture> pictures;

    /**
     * Constructs a new PictureRepository, setting up the JSON serializer with the file path for picture data,
     * and loading the existing pictures from this file into the memory.
     */
    public PictureRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(ResourcePath.PICTURE));
        this.pictures = load();
    }

    /**
     * Adds a {@link Picture} to the repository and saves the updated list to the JSON file.
     *
     * @param picture The picture to add to the repository.
     * @return Always returns {@code true}, indicating that the picture was successfully added.
     */
    @Override
    public boolean add(Picture picture) {
        pictures.add(picture);
        save();
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
        boolean removed = pictures.removeIf(picture -> picture.getId().equals(id));
        if (removed) save();
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
        return pictures.stream()
                .filter(picture -> picture.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Returns an unmodifiable list of all pictures currently stored in the repository.
     *
     * @return An unmodifiable list of {@link Picture} entities.
     */
    @Override
    public List<Picture> getAll() {
        return Collections.unmodifiableList(pictures);
    }

    /**
     * Loads the list of pictures from the JSON file into the repository's internal list.
     *
     * @return A list of {@link Picture} entities. Returns an empty list if there is an error during the file reading.
     */
    @Override
    public List<Picture> load() {
        try {
            return jsonSerializer.read(Picture.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Saves the current list of pictures to the JSON file.
     *
     * @throws IOException if there is an I/O error during writing to the file.
     */
    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(pictures);
    }
}
