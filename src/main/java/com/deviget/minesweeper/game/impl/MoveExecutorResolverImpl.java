package com.deviget.minesweeper.game.impl;

import com.deviget.minesweeper.game.MoveExecutor;
import com.deviget.minesweeper.game.MoveExecutorResolver;
import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.MoveType;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class works as a strategy resolver of a {@link MoveExecutor}
 * based on both a given {@link GameStatus} and a {@link MoveType}
 */
@Component
class MoveExecutorResolverImpl implements MoveExecutorResolver {

    private Map<String, MoveExecutor> moveExecutors;

    public MoveExecutorResolverImpl(List<MoveExecutor> executors) {
        /*
         * Creates a map containing all the strategies to execute a Move action
         */
        this.moveExecutors = executors.stream()
                .flatMap(processor -> processor.allowedGameStatuses().stream()
                        .map(gameStatus -> Pair.of(gameStatus, processor))
                        .flatMap(entry -> entry.getSecond().allowMoveTypes().stream()
                                .map(moveType -> Pair.of(generateKey(entry.getFirst(), moveType), entry.getSecond()))))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }

    public MoveExecutor resolveMoveExecutor(GameStatus gameStatus, MoveType moveType) {
        var key = generateKey(gameStatus, moveType);
        var moveExecutor = moveExecutors.get(key);

        if(moveExecutor == null) {
            throw new IllegalArgumentException("Cannot resolve MoveExecutor given this moveType: " + moveType.name());
        }

        return moveExecutor;
    }

    private String generateKey(GameStatus gameStatus, MoveType moveType) {
        return gameStatus.name() + "_" + moveType.name();
    }
}
