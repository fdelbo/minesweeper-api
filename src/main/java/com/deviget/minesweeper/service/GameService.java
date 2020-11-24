package com.deviget.minesweeper.service;

import com.deviget.minesweeper.model.api.CreateGameRequest;
import com.deviget.minesweeper.model.api.MakeAMoveRequest;
import com.deviget.minesweeper.model.document.Game;

/**
 * Interface related to manage a {@link Game}
 */
public interface GameService {

    /**
     * Creates a new {@link Game}
     * @param request used to create a {@link Game}
     * @return the {@link Game} created
     */
    Game create(CreateGameRequest request);

    /**
     * Makes a move in the {@link Game} board
     * @param gameId where the move is going to be made
     * @param moveRequest contains data related to the move
     * @return the game with the new status after make the move
     */
    Game makeAMove(String gameId, MakeAMoveRequest moveRequest);
}
