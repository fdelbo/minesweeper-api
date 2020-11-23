package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.model.Board;
import com.deviget.minesweeper.model.api.CreateGameRequest;
import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.repository.GameRepository;
import com.deviget.minesweeper.service.GameService;
import com.deviget.minesweeper.validator.CreateGameRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class GameServiceImpl implements GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
    private CreateGameRequestValidator requestValidator;
    private GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository, CreateGameRequestValidator requestValidator) {
        this.gameRepository = gameRepository;
        this.requestValidator = requestValidator;
    }

    @Override
    public Game create(CreateGameRequest request) {
        requestValidator.accept(request);
        logger.info("Creating game - Rows: {} - Columns {} - Mines: {}", request.getRows(), request.getColumns(),
                request.getMines());

        var board = new Board(request.getRows(), request.getColumns(), request.getMines());
        var game = new Game(request.getUserId(), board);
        board.setup();

        logger.info(board.toString());
        return gameRepository.save(game);
    }
}
