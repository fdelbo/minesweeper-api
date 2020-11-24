package com.deviget.minesweeper.game.impl;

import com.deviget.minesweeper.game.MoveExecutor;
import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.MoveType;
import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.repository.GameRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This {@link MoveExecutor} is a noop moveExecutor.
 * This implementation has to be used in all the {@link MoveType} when {@link GameStatus} is not 'PLAYING'
 */
@Component
class NoOpMoveExecutor extends MoveExecutor {

    public NoOpMoveExecutor(GameRepository gameRepository) {
        super(gameRepository);
    }

    @Override
    protected Game doExecute(Game game, int r, int c) {
        //Do nothing
        return game;
    }

    @Override
    public List<GameStatus> allowedGameStatuses() {
        return List.of(GameStatus.GAME_OVER, GameStatus.WON, GameStatus.PAUSE);
    }

    @Override
    public List<MoveType> allowMoveTypes() {
        return List.of(MoveType.FLAG, MoveType.FLIP, MoveType.MARK, MoveType.REMOVE_FLAG_OR_MARK);
    }
}
