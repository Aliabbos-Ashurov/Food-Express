package com.pdp.web.repository.picture;

import com.pdp.config.SQLConfiguration;
import com.pdp.enums.format.PictureFormat;
import com.pdp.web.model.picture.Picture;
import com.pdp.web.repository.BaseRepository;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import sql.helper.SQLHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
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
    private final SQLHelper sql = SQLConfiguration.getSQL();

    /**
     * Adds a {@link Picture} to the repository and saves the updated list to the JSON file.
     *
     * @param picture The picture to add to the repository.
     * @return Always returns {@code true}, indicating that the picture was successfully added.
     */
    @SneakyThrows
    @Override
    public boolean add(@NotNull Picture picture) {
        return sql.executeUpdate("INSERT INTO web.picture(name,format,width,height,image_url) VALUES (?,?,?,?,?);",
                picture.getName(), picture.getFormat(), picture.getWidth(), picture.getHeight(), picture.getImageUrl()) > 0;
    }

    /**
     * Removes a picture from the repository by its UUID and updates the JSON file if the picture is successfully removed.
     *
     * @param id The UUID of the picture to remove.
     * @return {@code true} if the picture was successfully found and removed; {@code false} otherwise.
     */
    @SneakyThrows
    @Override
    public boolean remove(@NotNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.picture WHERE id = ?;", id) > 0;
    }

    /**
     * Retrieves a picture by its UUID from the repository.
     *
     * @param id The UUID of the picture to find.
     * @return The picture if found, or {@code null} if no picture with such ID exists.
     */
    @Override
    public Picture findById(@NotNull UUID id) {
        return getAll().stream().filter(picture -> picture.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Returns an unmodifiable list of all pictures currently stored in the repository.
     *
     * @return An unmodifiable list of {@link Picture} entities.
     */
    @SneakyThrows
    @Override
    public List<Picture> getAll() {
        ResultSet resultSet = sql.executeQuery("SELECT * FROM web.picture;");
        List<Picture> pictures = new ArrayList<>();
        while (resultSet.next()) {
            Picture picture = new Picture();
            picture.setId(UUID.fromString(resultSet.getString("id")));
            picture.setName(resultSet.getString("name"));
            picture.setFormat(PictureFormat.valueOf(resultSet.getString("format")));
            picture.setWidth(resultSet.getInt("width"));
            picture.setHeight(resultSet.getInt("height"));
            picture.setImageUrl(resultSet.getString("image_url"));
            picture.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            pictures.add(picture);
        }
        return pictures;
    }
}
