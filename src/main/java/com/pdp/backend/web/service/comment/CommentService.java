package com.pdp.backend.web.service.comment;

import com.pdp.backend.web.model.comment.Comment;
import com.pdp.backend.web.repository.comment.CommentRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;
import java.util.UUID;


/**
 * Service interface responsible for providing comment-related functionalities.
 * This interface extends the BaseService and specializes in handling Comment entities.
 *
 * @see BaseService
 * @see Comment
 */
public interface CommentService extends BaseService<Comment, List<Comment>> {
    CommentRepository repository = CommentRepository.getInstance();

    /**
     * Fetches a list of comments associated with a specific food item identified by UUID.
     *
     * @param foodID The unique identifier of the food item for which to retrieve comments.
     * @return A list of {@link Comment} entities associated with the specified food item.
     */
    List<Comment> getComments(UUID foodID);
}
