package com.deviget.minesweeper.service;

import com.deviget.minesweeper.model.api.CreateUserRequest;
import com.deviget.minesweeper.model.document.User;

/**
 * Interface related to manage a {@link User}
 */
public interface UserService {

    /**
     * Creates a new {@link User}
     * @param request used to create a {@link User}
     * @return the {@link User} created
     */
    User create(CreateUserRequest request);
}
