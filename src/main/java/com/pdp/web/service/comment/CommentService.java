package com.pdp.web.service.comment;

import com.pdp.web.model.comment.Comment;
import com.pdp.web.repository.comment.CommentRepository;
import com.pdp.web.service.BaseService;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;


/**
 * Service interface responsible for providing comment-related functionalities.
 * This interface extends the {@link BaseService} and specializes in handling {@link Comment} entities.
 *
 * @see BaseService
 * @see Comment
 */
public interface CommentService extends BaseService<Comment, List<Comment>> {
    CommentRepository repository = new CommentRepository();

    /**
     * Fetches a list of comments associated with a specific food item identified by its UUID.
     *
     * @param foodID The UUID of the food item for which to retrieve comments.
     * @return A list of {@link Comment} entities associated with the specified food item.
     */
    List<Comment> getComments(@NonNull UUID foodID);
}
