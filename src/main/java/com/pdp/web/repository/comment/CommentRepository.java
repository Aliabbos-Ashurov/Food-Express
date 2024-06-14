package com.pdp.web.repository.comment;

import com.pdp.config.SQLConfiguration;
import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.comment.Comment;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import lombok.NonNull;
import lombok.SneakyThrows;
import sql.helper.SQLHelper;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Repository for managing {@code Comment} entities with CRUD operations.
 * Handles serialization and deserialization of {@code Comment} objects to and from JSON format,
 * enabling persistent storage of comments using {@link JsonSerializer}.
 * <p>
 * Inherits standard repository functionalities from {@link BaseRepository} to
 * operate on an in-memory list of {@code Comment}s that is kept consistent
 * with a JSON file specified by {@link JsonFilePath#PATH_COMMENT}.
 * <p>
 * All changes to the repository are immediately persisted to the backing JSON file,
 * ensuring data consistency across application restarts.
 * </p>
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 16:34
 */
public class CommentRepository implements BaseRepository<Comment, List<Comment>> {
    private final SQLHelper sql = SQLConfiguration.getSQL();

    @Override
    @SneakyThrows
    public boolean add(@NonNull Comment comment) {
        return sql.executeUpdate("INSERT INTO web.comment(food_id,text) VALUES(?,?);"
                , comment.getFoodID(), comment.getText()) > 0;
    }

    /**
     * Removes a {@code Comment} from the repository by its unique identifier and
     * persists the updated list of comments to the JSON storage.
     *
     * @param id The UUID of the {@code Comment} to be removed.
     * @return {@code true} if the {@code Comment} was found and removed,
     * {@code false} otherwise.
     * @throws IOException if any I/O error occurs during saving to the JSON file.
     */
    @Override
    @SneakyThrows
    public boolean remove(@NonNull UUID id) {
        return sql.executeUpdate("DELETE FROM web.comment WHERE id = ?;", id) > 0;
    }

    /**
     * Searches for a {@code Comment} within the repository using the provided UUID. The search
     * is performed on the in-memory list of comments, which is kept in sync with the JSON file.
     *
     * @param id The UUID of the {@code Comment} to find.
     * @return The {@code Comment} object if found, or {@code null} if no matching comment is found.
     */
    @Override
    public Comment findById(@NonNull UUID id) {
        return getAll().stream()
                .filter(comment -> comment.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all {@code Comment} objects currently held in the repository.
     * The full set of comments is obtained from the in-memory list which is synchronized
     * with the JSON storage file.
     *
     * @return A list of all {@code Comment} objects in the repository.
     */
    @Override
    @SneakyThrows
    public List<Comment> getAll() {
        List<Comment> comments = new ArrayList<>();
        ResultSet rs = sql.executeQuery("SELECT * FROM web.comment;");
        while (rs.next()) {
            Comment comment = new Comment();
            comment.setId(UUID.fromString(rs.getString(1)));
            comment.setFoodID(UUID.fromString(rs.getString(2)));
            comment.setText(rs.getString(3));
            comment.setCreatedAt(rs.getTimestamp(4).toLocalDateTime());
            comments.add(comment);
        }
        return comments;
    }
}
