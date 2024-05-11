package com.pdp.web.service.comment;

import com.pdp.utils.Validator;
import com.pdp.web.model.comment.Comment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Service implementation for managing comments.
 * This class uses a singleton pattern to manage instances.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentServiceImp implements CommentService {
    private static volatile CommentServiceImp instance;

    public static CommentServiceImp getInstance() {
        if (instance == null) {
            synchronized (CommentServiceImp.class) {
                if (instance == null) {
                    instance = new CommentServiceImp();
                }
            }
        }
        return instance;
    }

    /**
     * Retrieves all comments associated with a particular food item.
     *
     * @param foodID The UUID of the food item whose comments are to be retrieved.
     * @return A list of comments.
     */
    @Override
    public List<Comment> getComments(UUID foodID) {
        List<Comment> comments = getAll();
        return comments.stream()
                .filter(comment -> comment.getFoodID().equals(foodID))
                .toList();
    }

    /**
     * Adds a new comment to the repository.
     *
     * @param comment The Comment object to be added.
     * @return True if the comment was successfully added, false otherwise.
     */
    @Override
    public boolean add(Comment comment) {
        return repository.add(comment);
    }

    /**
     * Removes a comment based on its UUID.
     *
     * @param id The UUID of the comment to be removed.
     * @return True if the comment was successfully removed, false otherwise.
     */
    @Override
    public boolean remove(UUID id) {
        return repository.remove(id);
    }

    /**
     * Updates an existing comment.
     * The method is a placeholder currently not implemented.
     *
     * @param comment The comment object with updated information.
     * @return False as this operation is not supported.
     */
    @Override
    public boolean update(Comment comment) {
        return false;
    }

    /**
     * Searches for comments based on a query.
     *
     * @param query The search query.
     * @return A list of comments that match the query.
     */
    @Override
    public List<Comment> search(String query) {
        List<Comment> comments = getAll();
        return comments.stream()
                .filter(comment -> Validator.isValid(comment.getText(), query))
                .toList();
    }

    /**
     * Retrieves a comment by its unique ID.
     *
     * @param id The UUID of the comment to retrieve.
     * @return The comment if found, or null if not found.
     */
    @Override
    public Comment getByID(UUID id) {
        return repository.findById(id);
    }

    /**
     * Retrieves all comments.
     *
     * @return A list of all comments.
     */
    @Override
    public List<Comment> getAll() {
        return repository.getAll();
    }
}
