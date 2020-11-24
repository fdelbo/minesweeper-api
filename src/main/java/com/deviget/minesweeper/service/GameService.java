package com.deviget.minesweeper.service;

import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.api.ChangeGameStatusRequest;
import com.deviget.minesweeper.model.api.CreateGameRequest;
import com.deviget.minesweeper.model.api.MakeAMoveRequest;
import com.deviget.minesweeper.model.document.Game;

import java.util.List;

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

    /**
     * Change the {@link GameStatus} of a {@link Game}
     * @param gameId where the status is going to be changed
     * @param request used to change the {@link GameStatus}
     */
    void changeStatus(String gameId, ChangeGameStatusRequest request);

    /**
     * Retrieves a list of {@link Game} given a userId
     * @param userId to find the games
     * @return the list of {@link Game}
     */
    List<Game> retrieveByUserId(String userId);
}
