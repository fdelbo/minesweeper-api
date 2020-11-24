package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.model.api.ChangeGameStatusRequest;
import com.deviget.minesweeper.model.api.CreateGameRequest;
import com.deviget.minesweeper.model.api.GameResponse;
import com.deviget.minesweeper.model.api.MakeAMoveRequest;
import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.service.GameService;
import io.swagger.annotations.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameResponse> create(
            @ApiParam(value = "Data related to the game to be created")
            @RequestBody CreateGameRequest request) {

        var game = gameService.create(request);
        return ResponseEntity.ok(toResponseConverter.convert(game));
    }

    @ApiOperation("Allows to make a move in a given game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Move made successfully"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Game or User not found"),
            @ApiResponse(code = 503, message = "Service unavailable")
    })
    @PatchMapping(path = "{gameId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameResponse> click(
            @ApiParam(value = "Id related to the game you want to play")
            @PathVariable("gameId") String gameId,
            @ApiParam(value = "Data related to the move you want to make")
            @RequestBody MakeAMoveRequest moveRequest) {

        var game = gameService.makeAMove(gameId, moveRequest);
        return ResponseEntity.ok(toResponseConverter.convert(game));
    }

    @ApiOperation("Allows to set PAUSE/PLAYING status to a Game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status changed successfully"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Game not found"),
            @ApiResponse(code = 503, message = "Service unavailable")
    })
    @PatchMapping(path = "{gameId}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> tooglePauseOrPlay(
            @ApiParam(value = "Id related to the game you want to change the status")
            @PathVariable("gameId") String gameId,
            @ApiParam(value = "Date related to the game you want to change the status")
            @RequestBody ChangeGameStatusRequest request) {

        gameService.tooglePauseOrPlay(gameId, request);
        return ResponseEntity.ok().build();
    }

}
