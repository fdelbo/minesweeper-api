package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.model.api.CreateUserRequest;
import com.deviget.minesweeper.model.document.User;
import com.deviget.minesweeper.repository.UserRepository;
import com.deviget.minesweeper.service.UserService;
import com.deviget.minesweeper.validator.AnnotationBasedValidator;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AnnotationBasedValidator requestValidator;

    public UserServiceImpl(UserRepository userRepository, AnnotationBasedValidator requestValidator) {
        this.userRepository = userRepository;
        this.requestValidator = requestValidator;
    }

    @Override
    public User create(CreateUserRequest request) {
        requestValidator.accept(request);
        var user = new User(request.getName(), request.getLastName(), request.getUserName());

        return userRepository.save(user);
    }
}
