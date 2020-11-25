package com.deviget.minesweeper.game.impl;

import com.deviget.minesweeper.model.Board;
import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MarkCellActionExecutorTest {

    @Test
    void givenAMarkCellAction_thenCellIsMarkedAndTheGameIsStored() {
        //Given
        var row = 1;
        var column = 2;
        var gameRepository = mock(GameRepository.class);
        var executor = new MarkCellActionExecutor(gameRepository);
        var board = mock(Board.class);
        var game = new Game("user-id", board);

        //When
        Executable executable = () -> executor.execute(game, row, column);

        //Then
        assertDoesNotThrow(executable, "No exception is expected because the execute method should perform ok");
        verify(board, only()).toggleMark(eq(row), eq(column));
        verify(gameRepository, only()).save(eq(game));
    }
}
