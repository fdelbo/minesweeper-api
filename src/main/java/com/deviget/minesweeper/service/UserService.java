package com.deviget.minesweeper.service;

import com.deviget.minesweeper.model.api.CreateUserRequest;
import com.deviget.minesweeper.model.document.User;

public interface UserService {

    User create(CreateUserRequest user);
}
