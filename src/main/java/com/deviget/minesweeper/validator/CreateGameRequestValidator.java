package com.deviget.minesweeper.validator;

import com.deviget.minesweeper.model.api.CreateGameRequest;
import com.deviget.minesweeper.model.document.Game;

import java.util.function.Consumer;

/**
 * This validator validates that the needed data to create a new {@link Game} is valid
 */
@FunctionalInterface
public interface CreateGameRequestValidator extends Consumer<CreateGameRequest> {
}
