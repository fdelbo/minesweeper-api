package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.model.api.CreateUserRequest;
import com.deviget.minesweeper.model.api.GameResponse;
import com.deviget.minesweeper.model.api.UserResponse;
import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.model.document.User;
import com.deviget.minesweeper.service.GameService;
import com.deviget.minesweeper.service.UserService;
import io.swagger.annotations.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Users")
@RequestMapping("/users")
@RestController
public class UserController {

    private UserService userService;
    private GameService gameService;
    private Converter<User, UserResponse> toResponseConverter;
    private Converter<Game, GameResponse> toGameResponseConverter;

    public UserController(UserService userService, GameService gameService,
                          Converter<User, UserResponse> toResponseConverter,
                          Converter<Game, GameResponse> toGameResponseConverter) {
        this.userService = userService;
        this.gameService = gameService;
        this.toResponseConverter = toResponseConverter;
        this.toGameResponseConverter = toGameResponseConverter;
    }

    @ApiOperation("Allows to create a User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User created successfully"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 503, message = "Service unavailable")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> create(@RequestBody CreateUserRequest request) {
        var user = userService.create(request);
        return ResponseEntity.ok(toResponseConverter.convert(user));
    }

    @ApiOperation("Retrieves the games of a given user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Games retrieved"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 503, message = "Service unavailable")
    })
    @GetMapping(path = "{userId}/games", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GameResponse>> retrieveGamesByUser(
            @ApiParam(value = "UserId used to retrieve the games")
            @PathVariable("userId") String userId) {

        var games = gameService.retrieveByUserId(userId);
        var gamesResponse = games.stream()
                .map(g -> toGameResponseConverter.convert(g))
                .collect(Collectors.toList());

        return ResponseEntity.ok(gamesResponse);
    }
}
