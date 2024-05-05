package com.pdp.backend.web.repository.picture;

import com.pdp.backend.web.config.path.ResoucePath;
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
 * @author Aliabbos Ashurov
 * @since 04/May/2024  16:59
 **/
public class PictureRepository implements BaseRepository<Picture> {
    private final JsonSerializer<Picture> jsonSerializer;
    private final List<Picture> pictures;

    public PictureRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(ResoucePath.PICTURE));
        this.pictures = load();
    }

    @Override
    public boolean add(Picture picture) {
        pictures.add(picture);
        save();
        return true;
    }

    @Override
    public boolean remove(UUID id) {
        boolean removed = pictures.removeIf(picture -> picture.getId().equals(id));
        if (removed) save();
        return removed;
    }

    @Override
    public Picture findById(UUID id) {
        return pictures.stream()
                .filter(picture -> picture.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Picture> getAll() {
        return Collections.unmodifiableList(pictures);
    }

    @Override
    public List<Picture> load() {
        try {
            return jsonSerializer.read(Picture.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(pictures);
    }
}
