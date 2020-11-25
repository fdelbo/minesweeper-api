package com.deviget.minesweeper.validator;

import com.deviget.minesweeper.model.api.MakeAMoveRequest;
import com.deviget.minesweeper.model.document.Game;

import java.util.function.BiConsumer;

/**
 * This validator validates that the needed data to make an action within a {@link Game} is valid
 */
@FunctionalInterface
public interface MakeAMoveRequestAndGameValidator extends BiConsumer<MakeAMoveRequest, Game> {
}
