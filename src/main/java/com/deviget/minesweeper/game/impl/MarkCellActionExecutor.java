package com.deviget.minesweeper.game.impl;

import com.deviget.minesweeper.game.ActionExecutor;
import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.MoveType;
import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.repository.GameRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class MarkCellActionExecutor extends ActionExecutor {

    public MarkCellActionExecutor(GameRepository gameRepository) {
        super(gameRepository);
    }

    @Override
    protected Game doExecute(Game game, int r, int c) {
        var board = game.getBoard();
        board.toggleMark(r, c);

        return game;
    }

    @Override
    public List<GameStatus> allowedGameStatuses() {
        return List.of(GameStatus.PLAYING);
    }

    @Override
    public List<MoveType> allowMoveTypes() {
        return List.of(MoveType.MARK);
    }
}
