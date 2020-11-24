package com.deviget.minesweeper.game;

import com.deviget.minesweeper.model.Board;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.MoveType;
import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.repository.GameRepository;

import java.time.Instant;
import java.util.List;

/**
 * This class is an abstraction of a move execution implemented as a template method
 */
public abstract class MoveExecutor {

    private GameRepository gameRepository;

    public MoveExecutor(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    final public Game execute(Game game, int r, int c) {
        game = doExecute(game, r, c);
        return saveInRepository(game);
    }

    protected Game saveInRepository(Game game) {
        game.setLastMoveDate(Instant.now());
        return gameRepository.save(game);
    }

    /**
     * Action to be executed against the game {@link Board}
     * @param game where the action is going to be executed
     * @param r row that in combination with a column defines a {@link Cell}
     * @param c column that in combination with a row defines a {@link Cell}
     * @return the status of the {@link Game} after the executed action
     */
    protected abstract Game doExecute(Game game, int r, int c);

    /**
     * @return a list of allowed {@link GameStatus} which the executor is able to work
     */
    public abstract List<GameStatus> allowedGameStatuses();

    /**
     * @return a list of allowed {@link MoveType} which the executor is able to work
     */
    public abstract List<MoveType> allowMoveTypes();
}
