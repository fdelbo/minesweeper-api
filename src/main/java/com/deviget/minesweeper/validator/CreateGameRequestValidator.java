package com.deviget.minesweeper.validator;

import com.deviget.minesweeper.model.api.CreateGameRequest;

import java.util.function.Consumer;

@FunctionalInterface
public interface CreateGameRequestValidator extends Consumer<CreateGameRequest> {
}
