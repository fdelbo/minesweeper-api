package com.deviget.minesweeper.validator.impl;

import com.deviget.minesweeper.exception.ValidationException;
import com.deviget.minesweeper.validator.AnnotationBasedValidator;
import org.springframework.stereotype.Component;

import javax.validation.Validation;

/**
 * This validator validates the annotated fields of a given object.
 * The annotations used with the fields must be part of javax.validations
 */
@Component
class DefaultAnnotationBasedValidator implements AnnotationBasedValidator {

    @Override
    public void accept(Object object) {
        var violations = Validation.buildDefaultValidatorFactory()
                .getValidator()
                .validate(object);

        if(!violations.isEmpty()) {
            throw new ValidationException("Validation error", violations);
        }
    }
}
