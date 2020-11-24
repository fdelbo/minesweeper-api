package com.deviget.minesweeper.validator.impl;

import com.deviget.minesweeper.exception.ResourceNotFoundException;
import com.deviget.minesweeper.model.api.CreateGameRequest;
import com.deviget.minesweeper.model.document.User;
import com.deviget.minesweeper.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateGameRequestValidatorImplTest {

    @Test
    void whenUserDoesNotExist_thenThrowResourceNotFoundException() {
        //Given
        var userRepository = mock(UserRepository.class);
        var validator = new CreateGameRequestValidatorImpl(userRepository, (r) -> {});
        var request = createGameRequest(5, 5, 5);
        when(userRepository.findById(eq(request.getUserId()))).thenReturn(Optional.empty());

        //When
        Executable executable = () -> validator.accept(request);

        //Then
        var exception = assertThrows(ResourceNotFoundException.class, executable,
                "Validator is expected to fail because user should not be found");
        assertEquals("User with id [" + request.getUserId() + "] not found", exception.getMessage());
    }

    @Test
    void whenUserExists_thenNoExceptionIsExpected() {
        //Given
        var userRepository = mock(UserRepository.class);
        var validator = new CreateGameRequestValidatorImpl(userRepository, (r) -> {});
        var request = createGameRequest(5, 5, 5);
        when(userRepository.findById(eq(request.getUserId()))).thenReturn(Optional.of(new User()));

        //When
        Executable executable = () -> validator.accept(request);

        //Then
        assertDoesNotThrow(executable,"Validator is not expected to fail because request is valid");
    }

    private CreateGameRequest createGameRequest(int rows, int columns, int mines) {
        var req = new CreateGameRequest("user-id-test", rows, columns, mines);
        return req;
    }
}
