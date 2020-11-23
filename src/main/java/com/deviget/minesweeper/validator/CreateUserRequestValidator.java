package com.deviget.minesweeper.validator;

import com.deviget.minesweeper.model.api.CreateUserRequest;

import java.util.function.Consumer;

@FunctionalInterface
public interface CreateUserRequestValidator extends Consumer<CreateUserRequest> {
}
