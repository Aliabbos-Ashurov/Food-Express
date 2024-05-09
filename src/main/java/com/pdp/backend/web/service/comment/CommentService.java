package com.pdp.backend.web.service.comment;

import com.pdp.backend.web.model.comment.Comment;
import com.pdp.backend.web.repository.comment.CommentRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

public interface CommentService extends BaseService<Comment, List<Comment>> {
    CommentRepository repository = new CommentRepository();
}
