package com.deviget.minesweeper.exception;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = -7289698714163839225L;
    private List<String> violations;

    public ValidationException(String message, Set<ConstraintViolation<Object>> violations) {
        super(message);
        this.violations = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }

    public ValidationException(String message) {
        super(message);
        this.violations = List.of(message);
    }

    public List<String> getViolations() {
        return violations;
    }
}
