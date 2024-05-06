package com.pdp.backend.web.repository.comment;

import com.pdp.backend.web.config.path.ResourcePath;
import com.pdp.backend.web.model.comment.Comment;
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
 * Implements a repository for managing Comment entities in a JSON serialized format.
 * Offers CRUD operations for Comment objects such as add, remove, findById, and getAll.
 * <p>
 * Maintains an in-memory list of comments that is synchronized with a JSON file via
 * JsonSerializer to guarantee data persistence and integrity.
 *
 * @author Aliabbos Ashurov
 * @since 04/May/2024 16:34
 */
public class CommentRepository implements BaseRepository<Comment> {
    private final JsonSerializer<Comment> jsonSerializer;
    private final List<Comment> comments;

    /**
     * Constructs a CommentRepository, setting up a JsonSerializer with the correct
     * file path from ResourcePath and loading existing comments from the file.
     */
    public CommentRepository() {
        this.jsonSerializer = new JsonSerializer<>(Path.of(ResourcePath.COMMENT));
        this.comments = load();
    }

    @Override
    public boolean add(Comment comment) {
        comments.add(comment);
        save();
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
    public boolean remove(UUID id) {
        boolean removed = comments.removeIf((comment -> comment.getId().equals(id)));
        if (removed) save();
        return removed;
    }

    @Override
    public Comment findById(UUID id) {
        return comments.stream()
                .filter(comment -> comment.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Returns an unmodifiable list of all comments stored in the repository.
     *
     * @return An unmodifiable List of Comments.
     */
    @Override
    public List<Comment> getAll() {
        return Collections.unmodifiableList(comments);
    }

    @Override
    public List<Comment> load() {
        try {
            return jsonSerializer.read(Comment.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @SneakyThrows
    @Override
    public void save() {
        jsonSerializer.write(comments);
    }
}
