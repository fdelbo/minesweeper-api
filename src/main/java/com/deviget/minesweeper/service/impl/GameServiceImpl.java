package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.exception.ResourceNotFoundException;
import com.deviget.minesweeper.game.Board;
import com.deviget.minesweeper.game.MoveExecutorResolver;
import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.MoveType;
import com.deviget.minesweeper.model.api.ChangeGameStatusRequest;
import com.deviget.minesweeper.model.api.CreateGameRequest;
import com.deviget.minesweeper.model.api.MakeAMoveRequest;
import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.repository.GameRepository;
import com.deviget.minesweeper.service.GameService;
import com.deviget.minesweeper.validator.AnnotationBasedValidator;
import com.deviget.minesweeper.validator.ChangeGameStatusRequestValidator;
import com.deviget.minesweeper.validator.CreateGameRequestValidator;
import com.deviget.minesweeper.validator.MakeAMoveRequestAndGameValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class GameServiceImpl implements GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    private CreateGameRequestValidator createRequestValidator;
    private AnnotationBasedValidator annotationBasedValidator;
    private MakeAMoveRequestAndGameValidator moveRequestAndGameValidator;
    private ChangeGameStatusRequestValidator changeGameStatusRequestValidator;
    private MoveExecutorResolver moveExecutorResolver;
    private GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository, CreateGameRequestValidator requestValidator,
                           AnnotationBasedValidator annotationBasedValidator,
                           MakeAMoveRequestAndGameValidator moveRequestAndGameValidator,
                           MoveExecutorResolver moveExecutorResolver,
                           ChangeGameStatusRequestValidator changeGameStatusRequestValidator) {
        this.gameRepository = gameRepository;
        this.createRequestValidator = requestValidator;
        this.annotationBasedValidator = annotationBasedValidator;
        this.moveRequestAndGameValidator = moveRequestAndGameValidator;
        this.changeGameStatusRequestValidator = changeGameStatusRequestValidator;
        this.moveExecutorResolver = moveExecutorResolver;
    }

    @Override
    public Game create(CreateGameRequest request) {
        createRequestValidator.accept(request);
        logger.info("Creating game - Rows: {} - Columns {} - Mines: {}", request.getRows(), request.getColumns(),
                request.getMines());

        var board = new Board(request.getRows(), request.getColumns(), request.getMines());
        var game = new Game(request.getUserId(), board);
        board.setup();

        logger.info(board.toString());
        return gameRepository.save(game);
    }

    @Override
    public Game makeAMove(String gameId, MakeAMoveRequest request) {
        annotationBasedValidator.accept(request);
        logger.info("Making a move of type [{}] - Row: {} - Column: {}", request.getType(), request.getRow(),
                request.getColumn());

        var game = findGameInRepository(gameId, request.getUserId());
        moveRequestAndGameValidator.accept(request, game);

        var moveExecutor = moveExecutorResolver.resolveMoveExecutor(game.getBoard().getGameStatus(),
                MoveType.valueOf(request.getType()));

        //Makes the move
        game = moveExecutor.execute(game, request.getRow(), request.getColumn());

        logger.info(game.getBoard().toString());
        return game;
    }

    @Override
    public void changeStatus(String gameId, ChangeGameStatusRequest request) {
        changeGameStatusRequestValidator.accept(request);
        logger.info("Changing status of game [{}] to [{}]", gameId, request.getStatus());

        //Find and change the status of a Game
        var game = findGameInRepository(gameId, request.getUserId());
        game.getBoard().setStatus(GameStatus.valueOf(request.getStatus()));

        gameRepository.save(game);
    }

    @Override
    public List<Game> retrieveByUserId(String userId) {
        return gameRepository.findByUserId(userId);
    }

    private Game findGameInRepository(String gameId, String userId) {
        return gameRepository.findByIdAndUserId(gameId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Game with id [" + gameId + "] and userId ["
                        + userId + "] not found"));
    }
}
