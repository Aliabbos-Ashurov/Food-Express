package com.pdp.web.service.user;

import com.pdp.web.model.user.User;
import com.pdp.web.repository.user.UserRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * Interface for services relating to user operations.
 * This extends BaseService to provide common CRUD operations specific to User entities.
 */
public interface UserService extends BaseService<User, List<User>> {
    UserRepository repository = UserRepository.getInstance();
}
