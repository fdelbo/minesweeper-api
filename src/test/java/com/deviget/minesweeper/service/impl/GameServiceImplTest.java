package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.Application;
import com.deviget.minesweeper.exception.ResourceNotFoundException;
import com.deviget.minesweeper.exception.ValidationException;
import com.deviget.minesweeper.model.CellStatus;
import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.api.ChangeGameStatusRequest;
import com.deviget.minesweeper.model.api.CreateGameRequest;
import com.deviget.minesweeper.model.api.MakeAMoveRequest;
import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.model.document.User;
import com.deviget.minesweeper.repository.GameRepository;
import com.deviget.minesweeper.repository.UserRepository;
import com.deviget.minesweeper.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = Application.class)
class GameServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    @Test
    void givenAnInvalidRequest_whenCreateGame_thenValidationExceptionIsExpected() {
        //Given
        var invalidRows = 0;
        var request = new CreateGameRequest("user-id", invalidRows, 2, 1);

        //When
        Executable executable = () -> gameService.create(request);

        //Then
        assertThrows(ValidationException.class, executable,
                "Request is invalid so ValidationException should be thrown");
    }

    @Test
    void givenANonExistingUser_whenCreateGame_thenReturnResourceNotFoundException() {
        //Given
        var nonExistingUser = "user-id-test";
        var request = new CreateGameRequest(nonExistingUser, 2, 2, 1);

        //When
        Executable executable = () -> gameService.create(request);

        //Then
        assertThrows(ResourceNotFoundException.class, executable,
                "User doesn't exist so ResourceNotFoundException should be thrown");
    }

    @Test
    void givenAValidRequest_whenCreateGame_thenReturnAGame() {
        //Given
        var user = userRepository.save(new User("Name", "Last name", "username001"));
        var request = new CreateGameRequest(user.getId(), 2, 2, 1);

        //When
        var game = gameService.create(request);

        //Then
        assertNotNull(game);
        assertNotNull(game.getId());
        assertNotNull(game.getCreatedDate());
        assertNotNull(game.getBoard());
        assertEquals(user.getId(), game.getUserId());
        assertEquals(GameStatus.PLAYING, game.getBoard().getGameStatus());
        assertNull(game.getLastMoveDate());
        assertNull(game.getLastResumeDate());
        assertTrue(game.getBoard().getCells().length > 0);
        assertTrue(gameRepository.existsById(game.getId()));
    }

    @Test
    void givenAnInvalidRequest_whenMakeAMove_thenThrowValidationException() {
        //Given
        var invalidMoveType = "INVALID_TYPE";
        var invalidRow = -1;
        var request = new MakeAMoveRequest("user-id", invalidMoveType, invalidRow, 0);

        //When
        Executable executable = () -> gameService.makeAMove("game-id", request);

        //Then
        var exception = assertThrows(ValidationException.class, executable,
                "Request is not valid so ValidationException should be thrown");
        assertEquals(2, exception.getViolations().size());
    }

    @Test
    void givenANonExistingGame_whenMakeAMove_thenThrowResourceNotFoundException() {
        //Given
        var user = userRepository.save(new User("Name", "Last name", "username002"));
        var request = new MakeAMoveRequest(user.getId(), "FLAG", 0, 0);

        //When
        Executable executable = () -> gameService.makeAMove("non-existing-game-id", request);

        //Then
        var exception = assertThrows(ResourceNotFoundException.class, executable,
                "Given gameId doesn't exist so ValidationException should be thrown");
    }

    @Test
    void givenAValidRequest_whenMakeAMove_thenReturnTheGameWithNewState() {
        //Given
        var column = 0;
        var row = 0;
        var user = userRepository.save(new User("Name", "Last name", "username003"));
        var existingGame = createAGame(user.getId());
        var request = new MakeAMoveRequest(user.getId(), "FLAG", row, column);

        //When
        var game = gameService.makeAMove(existingGame.getId(), request);

        //Then
        assertNotNull(game);
        assertNotNull(game.getLastMoveDate());
        assertEquals(CellStatus.FLAGGED, game.getBoard().getCells()[row][column].getStatus());
    }

    @Test
    void givenNewGameAndAValidRequest_whenTooglePauseOrPlay_thenTheGameStatusIsPaused() {
        //Given
        var user = userRepository.save(new User("Name", "Last name", "username004"));
        var existingGame = createAGame(user.getId());
        var request = new ChangeGameStatusRequest(user.getId());

        //When
        gameService.tooglePauseOrPlay(existingGame.getId(), request);

        //Then
        var game = gameRepository.findById(existingGame.getId()).get();
        assertEquals(GameStatus.PAUSE, game.getBoard().getGameStatus());
    }

    @Test
    void givenAPausedGameAndAValidRequest_whenTooglePauseOrPlay_thenTheGameStatusIsPlaying() {
        //Given
        var user = userRepository.save(new User("Name", "Last name", "username004"));
        var existingGame = createAGame(user.getId());
        existingGame.getBoard().tooglePauseOrPlaying();
        gameRepository.save(existingGame);
        var request = new ChangeGameStatusRequest(user.getId());

        //When
        gameService.tooglePauseOrPlay(existingGame.getId(), request);

        //Then
        var game = gameRepository.findById(existingGame.getId()).get();
        assertEquals(GameStatus.PLAYING, game.getBoard().getGameStatus());
    }

    @Test
    void givenAUserWithAssociatedGames_whenRetrieveByUserId_thenReturnTheGames() {
        //Given
        var user = userRepository.save(new User("Name", "Last name", "username004"));
        createAGame(user.getId());
        createAGame(user.getId());

        //When
        var games = gameService.retrieveByUserId(user.getId());

        //Then
        assertFalse(games.isEmpty());
        assertEquals(2, games.size());
        assertNotEquals(games.get(0), games.get(1));
    }

    private Game createAGame(String userId) {
        var createGameRequest = new CreateGameRequest(userId, 2, 2, 1);
        return gameService.create(createGameRequest);
    }
}
