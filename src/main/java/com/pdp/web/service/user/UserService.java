package com.pdp.web.service.user;

import com.pdp.web.model.user.User;
import com.pdp.web.repository.user.UserRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * Interface for services related to managing user entities.
 * This service provides basic CRUD operations for user entities.
 *
 * @see User
 * @see UserRepository
 */
public interface UserService extends BaseService<User, List<User>> {
    /**
     * The singleton instance of the UserRepository.
     */
    UserRepository repository = new UserRepository();
}
