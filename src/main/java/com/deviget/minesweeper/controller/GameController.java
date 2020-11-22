package com.deviget.minesweeper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Games")
@RequestMapping("/games")
@RestController
public class GameController {

    @ApiOperation("Allows to create a new game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game created successfully"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 503, message = "Service unavailable")
    })
    @PostMapping
    public void create() {
        //TODO
    }
}
