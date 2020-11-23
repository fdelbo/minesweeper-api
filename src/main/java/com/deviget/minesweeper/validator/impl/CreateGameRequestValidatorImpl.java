package com.deviget.minesweeper.validator.impl;

import com.deviget.minesweeper.exception.ResourceNotFoundException;
import com.deviget.minesweeper.model.api.CreateGameRequest;
import com.deviget.minesweeper.repository.UserRepository;
import com.deviget.minesweeper.validator.AnnotationBasedValidator;
import com.deviget.minesweeper.validator.CreateGameRequestValidator;
import org.springframework.stereotype.Component;

@Component
class CreateGameRequestValidatorImpl implements CreateGameRequestValidator {

    private UserRepository userRepository;
    private AnnotationBasedValidator annotationBasedValidator;

    public CreateGameRequestValidatorImpl(UserRepository userRepository, AnnotationBasedValidator annotationBasedValidator) {
        this.userRepository = userRepository;
        this.annotationBasedValidator = annotationBasedValidator;
    }

    @Override
    public void accept(CreateGameRequest createGameRequest) {
        annotationBasedValidator.accept(createGameRequest);

        userRepository.findById(createGameRequest.getUserId()).
                orElseThrow(() -> new ResourceNotFoundException("User with id [" + createGameRequest.getUserId() + "] not found"));
    }
}
