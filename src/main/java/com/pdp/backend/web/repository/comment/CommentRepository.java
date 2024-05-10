package com.pdp.backend.web.repository.comment;

import com.pdp.backend.web.model.comment.Comment;
import com.pdp.backend.web.repository.BaseRepository;
import com.pdp.json.serializer.JsonSerializer;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Implements a repository for managing Comment entities in a JSON serialized format.
 * Offers CRUD operations for Comment objects such as add, remove, findById, and getAll.
 * <p>
 * Maintains an in-memory list of comments that is synchronized with a JSON file via
 * JsonSerializer to guarantee data persistence and integrity.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 16:34
 */
public class CommentRepository implements BaseRepository<Comment, List<Comment>> {
    private static volatile CommentRepository instance;
    private static JsonSerializer<Comment> jsonSerializer;

    private CommentRepository() {
    }

    public static CommentRepository getInstance() {
        if (instance == null) {
            synchronized (CommentRepository.class) {
                if (instance == null) {
                    instance = new CommentRepository();
                    jsonSerializer = new JsonSerializer<>(Path.of(PATH_COMMENT));
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
     * Removes a Comment from the repository identified by its UUID and saves the updated
     * list to the JSON file.
     *
     * @param id The UUID of the Comment to remove.
     * @return True if the removal was successful, false otherwise.
     */
    @Override
    public boolean remove(@NonNull UUID id) {
        List<Comment> comments = load();
        boolean removed = comments.removeIf((comment -> comment.getId().equals(id)));
        if (removed) save(comments);
        return removed;
    }

    @Override
    public Comment findById(@NonNull UUID id) {
        List<Comment> comments = load();
        return comments.stream()
                .filter(comment -> comment.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Comment> getAll() {
        return load();
    }

    @SneakyThrows
    @Override
    public List<Comment> load() {
        return jsonSerializer.read(Comment.class);
    }

    @SneakyThrows
    @Override
    public void save(@NonNull List<Comment> comments) {
        jsonSerializer.write(comments);
    }
}
