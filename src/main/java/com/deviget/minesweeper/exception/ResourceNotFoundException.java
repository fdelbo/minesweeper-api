package com.deviget.minesweeper.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -7134675846519255767L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
