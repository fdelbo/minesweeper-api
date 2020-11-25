package com.deviget.minesweeper.validator.impl;

import com.deviget.minesweeper.exception.ValidationException;
import com.deviget.minesweeper.model.Board;
import com.deviget.minesweeper.model.api.MakeAMoveRequest;
import com.deviget.minesweeper.model.document.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MoveRequestAndGameValidatorImplTest {

    @Test
    void givenAValidRequestAndGame_thenNoExceptionIsExpected() {
        //Given
        var validator = new ActionRequestAndGameValidatorImpl();
        var request = createRequest("user-id-test", "FLIP", 1, 1);
        var game = createGame(4, 4);

        //When
        Executable executable = () -> validator.accept(request, game);

        //Then
        assertDoesNotThrow(executable, "Validator is expected not to fail");
    }

    @Test
    void givenARequestWithInvalidRowRange_thenValidationExceptionIsExpected() {
        //Given
        var invalidRow = 4;
        var validator = new ActionRequestAndGameValidatorImpl();
        var request = createRequest("user-id-test", "FLIP", invalidRow, 1);
        var game = createGame(4, 4);

        //When
        Executable executable = () -> validator.accept(request, game);

        //Then
        var exception = assertThrows(ValidationException.class, executable,
                "Validator is expected to fail");
        assertEquals("Invalid request", exception.getMessage());
        assertTrue(exception.getViolations().size() == 1);
        assertTrue(exception.getViolations().contains("Row is greater than game board bounds"));
    }

    @Test
    void givenARequestWithInvalidColumnRange_thenValidationExceptionIsExpected() {
        //Given
        var invalidColumn = 4;
        var validator = new ActionRequestAndGameValidatorImpl();
        var request = createRequest("user-id-test", "FLIP", 1, invalidColumn);
        var game = createGame(4, 4);

        //When
        Executable executable = () -> validator.accept(request, game);

        //Then
        var exception = assertThrows(ValidationException.class, executable,
                "Validator is expected to fail");
        assertEquals("Invalid request", exception.getMessage());
        assertTrue(exception.getViolations().size() == 1);
        assertTrue(exception.getViolations().contains("Column is greater than game board bounds"));
    }

    @Test
    void givenARequestWithInvalidColumnAndRowRange_thenValidationExceptionIsExpected() {
        //Given
        var invalidRow = 4;
        var invalidColumn = 4;
        var validator = new ActionRequestAndGameValidatorImpl();
        var request = createRequest("user-id-test", "FLIP", invalidRow, invalidColumn);
        var game = createGame(4, 4);

        //When
        Executable executable = () -> validator.accept(request, game);

        //Then
        var exception = assertThrows(ValidationException.class, executable,
                "Validator is expected to fail");
        assertEquals("Invalid request", exception.getMessage());
        assertTrue(exception.getViolations().size() == 2);
        assertTrue(exception.getViolations().contains("Row is greater than game board bounds"));
        assertTrue(exception.getViolations().contains("Column is greater than game board bounds"));
    }

    private Game createGame(int rows, int columns) {
        var board = mock(Board.class);
        when(board.getColumnsCount()).thenReturn(columns);
        when(board.getRowsCount()).thenReturn(rows);

        return new Game("user-id-test", board);
    }

    private MakeAMoveRequest createRequest(String userId, String type, int row, int column) {
        return new MakeAMoveRequest(userId, type, row, column);
    }
}
