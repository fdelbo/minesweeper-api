package com.deviget.minesweeper.game.impl;

import com.deviget.minesweeper.game.ActionExecutor;
import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.MoveType;
import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.repository.GameRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class FlipCellActionExecutor extends ActionExecutor {

    public FlipCellActionExecutor(GameRepository gameRepository) {
        super(gameRepository);
    }

    @Override
    protected Game doExecute(Game game, int r, int c) {
        var board = game.getBoard();
        board.flipCell(r, c);

        return game;
    }

    @Override
    public List<GameStatus> allowedGameStatuses() {
        return List.of(GameStatus.PLAYING);
    }

    @Override
    public List<MoveType> allowMoveTypes() {
        return List.of(MoveType.FLIP);
    }
}
