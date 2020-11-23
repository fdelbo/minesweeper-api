package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.model.api.CreateUserRequest;
import com.deviget.minesweeper.model.document.User;
import com.deviget.minesweeper.repository.UserRepository;
import com.deviget.minesweeper.service.UserService;
import com.deviget.minesweeper.validator.CreateUserRequestValidator;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private CreateUserRequestValidator requestValidator;

    public UserServiceImpl(UserRepository userRepository, CreateUserRequestValidator requestValidator) {
        this.userRepository = userRepository;
        this.requestValidator = requestValidator;
    }

    @Override
    public User create(CreateUserRequest request) {
        requestValidator.accept(request);
        var user = new User(request.getName(), request.getLastName());

        return userRepository.save(user);
    }
}
