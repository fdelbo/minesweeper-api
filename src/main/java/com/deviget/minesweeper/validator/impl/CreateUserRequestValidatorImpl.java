package com.deviget.minesweeper.validator.impl;

import com.deviget.minesweeper.model.api.CreateUserRequest;
import com.deviget.minesweeper.validator.AnnotationBasedValidator;
import com.deviget.minesweeper.validator.CreateUserRequestValidator;
import org.springframework.stereotype.Component;

@Component
class CreateUserRequestValidatorImpl implements CreateUserRequestValidator {

    private AnnotationBasedValidator annotationBasedValidator;

    public CreateUserRequestValidatorImpl(AnnotationBasedValidator annotationBasedValidator) {
        this.annotationBasedValidator = annotationBasedValidator;
    }

    @Override
    public void accept(CreateUserRequest createUserRequest) {
        annotationBasedValidator.accept(createUserRequest);
    }
}
