package com.deviget.minesweeper.validator;

import com.deviget.minesweeper.model.api.MakeAMoveRequest;
import com.deviget.minesweeper.model.document.Game;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface MakeAnActionRequestAndGameValidator extends BiConsumer<MakeAMoveRequest, Game> {
}
