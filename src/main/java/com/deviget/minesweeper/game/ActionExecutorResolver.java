package com.deviget.minesweeper.game;

import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.MoveType;

@FunctionalInterface
public interface ActionExecutorResolver {

    /**
     * This methods try to resolve a {@link ActionExecutor} given a {@link GameStatus} and a {@link MoveType}
     * @param gameStatus to resolve the {@link ActionExecutor} in combination with a {@link MoveType}
     * @param moveType to resolve the {@link ActionExecutor} in combination with a {@link GameStatus}
     * @return a {@link ActionExecutor}
     */
    ActionExecutor resolveActionExecutor(GameStatus gameStatus, MoveType moveType);
}
