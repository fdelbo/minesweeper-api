package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.model.api.CreateUserRequest;
import com.deviget.minesweeper.model.api.UserResponse;
import com.deviget.minesweeper.model.document.User;
import com.deviget.minesweeper.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Users")
@RequestMapping("/users")
@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("Allows to create a User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User created successfully"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 503, message = "Service unavailable")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> create(@RequestBody CreateUserRequest request) {
        var user = userService.create(new User(request.getName(), request.getLastName()));

        var userResponse = new UserResponse(user.getId(), user.getName(), user.getLastName());
        return ResponseEntity.ok(userResponse);
    }
}
