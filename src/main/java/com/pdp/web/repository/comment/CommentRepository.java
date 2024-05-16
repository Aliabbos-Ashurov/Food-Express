package com.pdp.web.repository.comment;

import com.pdp.utils.serializer.JsonSerializer;
import com.pdp.web.model.comment.Comment;
import com.pdp.web.repository.BaseRepository;
import com.pdp.config.jsonFilePath.JsonFilePath;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Path;
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
    private static volatile CommentRepository instance;
    private static JsonSerializer<Comment> jsonSerializer;

    private CommentRepository() {
    }

    /**
     * Retrieves the singleton instance of {@code CommentRepository}.
     * Initialization of this instance is performed in a thread-safe manner.
     *
     * @return The singleton instance of {@code CommentRepository}.
     */
    public static CommentRepository getInstance() {
        if (instance == null) {
            synchronized (CommentRepository.class) {
                if (instance == null) {
                    instance = new CommentRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(JsonFilePath.PATH_COMMENT));
                }
            }
        }
        return instance;
    }

    @Override
    public boolean add(@NonNull Comment comment) {
        List<Comment> comments = load();
        comments.add(comment);
        save(comments);
        return true;
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
    public boolean remove(@NonNull UUID id) {
        List<Comment> comments = load();
        boolean removed = comments.removeIf((comment -> comment.getId().equals(id)));
        if (removed) save(comments);
        return removed;
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
        List<Comment> comments = load();
        return comments.stream()
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
    public List<Comment> getAll() {
        return load();
    }

    @SneakyThrows
    @Override
    public List<Comment> load() {
        return jsonSerializer.read(Comment.class);
    }

    /**
     * Saves the current list of {@code Comment}s to the JSON file.
     * This involves serializing the list using {@link JsonSerializer} and writing it to the path
     * provided by {@link JsonFilePath#PATH_COMMENT}.
     *
     * @param comments The list of {@code Comment}s to be saved.
     * @throws IOException if any I/O error occurs during writing to the JSON file.
     */
    @SneakyThrows
    @Override
    public void save(@NonNull List<Comment> comments) {
        jsonSerializer.write(comments);
    }
}
