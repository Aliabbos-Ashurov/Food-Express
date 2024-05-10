package com.pdp.backend.web.service.user;

import com.pdp.backend.web.model.user.User;
import com.pdp.backend.web.repository.user.UserRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

/**
 * Interface for services relating to user operations.
 * This extends BaseService to provide common CRUD operations specific to User entities.
 */
public interface UserService extends BaseService<User, List<User>> {
    UserRepository repository = UserRepository.getInstance();
}
