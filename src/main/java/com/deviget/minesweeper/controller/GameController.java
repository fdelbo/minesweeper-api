package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.model.api.CreateGameRequest;
import com.deviget.minesweeper.model.api.GameResponse;
import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.service.GameService;
import io.swagger.annotations.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Games")
@RequestMapping("/games")
@RestController
public class GameController {

    private GameService gameService;
    private Converter<Game, GameResponse> toResponseConverter;

    public GameController(GameService gameService, Converter<Game, GameResponse> converter) {
        this.gameService = gameService;
        this.toResponseConverter = converter;
    }

    @ApiOperation("Allows to create a new game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game created successfully"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "User with the given userId not found"),
            @ApiResponse(code = 503, message = "Service unavailable")
    })
    @PostMapping
    public ResponseEntity<GameResponse> create(
            @ApiParam(value = "Data related to the game to be created")
            @RequestBody CreateGameRequest request) {

        var game = gameService.create(request);
        return ResponseEntity.ok(toResponseConverter.convert(game));
    }
}
