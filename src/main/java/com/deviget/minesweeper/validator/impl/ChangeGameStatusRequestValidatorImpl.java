package com.deviget.minesweeper.validator.impl;

import com.deviget.minesweeper.exception.ValidationException;
import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.api.ChangeGameStatusRequest;
import com.deviget.minesweeper.validator.AnnotationBasedValidator;
import com.deviget.minesweeper.validator.ChangeGameStatusRequestValidator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class ChangeGameStatusRequestValidatorImpl implements ChangeGameStatusRequestValidator {

    private static final Set<String> validStatuses = Set.of(GameStatus.PLAYING.name(), GameStatus.PAUSE.name());
    private AnnotationBasedValidator annotationBasedValidator;

    public ChangeGameStatusRequestValidatorImpl(AnnotationBasedValidator annotationBasedValidator) {
        this.annotationBasedValidator = annotationBasedValidator;
    }

    @Override
    public void accept(ChangeGameStatusRequest request) {
        annotationBasedValidator.accept(request);

        var status = request.getStatus();
        if(!validStatuses.contains(status)) {
            throw new ValidationException("Status [" + status + "] is not allowed to be set");
        }
    }
}
