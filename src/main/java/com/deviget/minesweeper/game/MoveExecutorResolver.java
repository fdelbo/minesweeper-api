package com.deviget.minesweeper.game;

import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.MoveType;

@FunctionalInterface
public interface MoveExecutorResolver {

    /**
     * This methods try to resolve a {@link MoveExecutor} given a {@link GameStatus} and a {@link MoveType}
     * @param gameStatus to resolve the {@link MoveExecutor} in combination with a {@link MoveType}
     * @param moveType to resolve the {@link MoveExecutor} in combination with a {@link GameStatus}
     * @return a {@link MoveExecutor}
     */
    MoveExecutor resolveMoveExecutor(GameStatus gameStatus, MoveType moveType);
}
