package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.exception.ResourceNotFoundException;
import com.deviget.minesweeper.model.Board;
import com.deviget.minesweeper.model.api.CreateGameRequest;
import com.deviget.minesweeper.model.api.MakeAMoveRequest;
import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.repository.GameRepository;
import com.deviget.minesweeper.service.GameService;
import com.deviget.minesweeper.validator.AnnotationBasedValidator;
import com.deviget.minesweeper.validator.CreateGameRequestValidator;
import com.deviget.minesweeper.validator.MakeAMoveRequestAndGameValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class GameServiceImpl implements GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
    private CreateGameRequestValidator createRequestValidator;
    private AnnotationBasedValidator annotationBasedValidator;
    private MakeAMoveRequestAndGameValidator moveRequestAndGameValidator;
    private GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository, CreateGameRequestValidator requestValidator,
                           AnnotationBasedValidator annotationBasedValidator,
                           MakeAMoveRequestAndGameValidator moveRequestAndGameValidator) {
        this.gameRepository = gameRepository;
        this.createRequestValidator = requestValidator;
        this.annotationBasedValidator = annotationBasedValidator;
        this.moveRequestAndGameValidator = moveRequestAndGameValidator;
    }

    @Override
    public Game create(CreateGameRequest request) {
        createRequestValidator.accept(request);
        logger.debug("Creating game - Rows: {} - Columns {} - Mines: {}", request.getRows(), request.getColumns(),
                request.getMines());

        var board = new Board(request.getRows(), request.getColumns(), request.getMines());
        var game = new Game(request.getUserId(), board);
        board.setup();

        logger.debug(board.toString());
        return gameRepository.save(game);
    }

    @Override
    public Game click(String gameId, MakeAMoveRequest request) {
        annotationBasedValidator.accept(request);
        logger.debug("Making a move of type {} - Row: {} - Column: {}", request.getType(), request.getRow(),
                request.getColumn());

        var game = findGame(gameId, request.getUserId());
        moveRequestAndGameValidator.accept(request, game);

        game.getBoard().flipCell(request.getRow(), request.getColumn());
        game = gameRepository.save(game);

        logger.debug(game.getBoard().toString());
        return game;
    }

    private Game findGame(String gameId, String userId) {
        return gameRepository.findByIdAndUserId(gameId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Game with id [" + gameId + "] and userId ["
                        + userId + "] not found"));
    }
}
