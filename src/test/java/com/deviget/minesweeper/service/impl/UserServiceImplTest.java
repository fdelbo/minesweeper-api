package com.deviget.minesweeper.service.impl;

import com.deviget.minesweeper.Application;
import com.deviget.minesweeper.exception.ValidationException;
import com.deviget.minesweeper.model.api.CreateUserRequest;
import com.deviget.minesweeper.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void givenANotValidRequest_whenCreate_thenThrowValidationException() {
        //Given
        var invalidName = "   ";
        var request = new CreateUserRequest(invalidName, "last-name-test", "user-name-test");

        //When
        Executable executable = () -> userService.create(request);

        //Then
        assertThrows(ValidationException.class, executable,
                "Request is not valid so ValidationException should be thrown");
    }

    @Test
    void givenAValidRequest_whenCreate_thenReturnAUser() {
        //Given
        var request = new CreateUserRequest("name-test", "last-name-test", "user-name-test");

        //When
        var user = userService.create(request);

        //Then
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(request.getName(), user.getName());
        assertEquals(request.getLastName(), user.getLastName());
        assertEquals(request.getUserName(), user.getUserName());
    }
}
