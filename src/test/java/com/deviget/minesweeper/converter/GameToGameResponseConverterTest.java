package com.deviget.minesweeper.converter;

import com.deviget.minesweeper.model.Board;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.CellStatus;
import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.api.CellResponse;
import com.deviget.minesweeper.model.document.Game;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameToGameResponseConverterTest {

    @Test
    void givenAGame_thenReturnAGameResponse() {
        //Given
        var converter = new GameToGameResponseConverter();
        var game = createGame();

        //When
        var converted = converter.convert(game);

        //Then
        assertNotNull(converted);
        assertEquals(game.getLastMoveDate().toString(), converted.getLastMoveDate());
        assertEquals(game.getLastResumeDate().toString(), converted.getLastResumeDate());
        assertEquals(game.getCreatedDate().toString(), converted.getCreatedDate());
        assertEquals(game.getUserId(), converted.getUserId());
        assertEquals(game.getBoard().getGameStatus().name(), converted.getGameStatus());
        assertEquals(game.getBoard().getRowsCount(), converted.getRows());
        assertEquals(game.getBoard().getColumnsCount(), converted.getColumns());
        assertEquals(game.getBoard().getMinesCount(), converted.getMines());
        assertEquals(game.getBoard().getCells().length, converted.getCells().size());

        CellResponse cellResponse = converted.getCells().get(0);
        Cell cell = game.getBoard().getCells()[0][0];

        assertEquals(cell.isMine(), cellResponse.isMine());
        assertEquals(cell.getRow(), cellResponse.getRow());
        assertEquals(cell.getColumn(), cellResponse.getColumn());
        assertEquals(cell.getNearbyMines(), cellResponse.getNearbyMines());
        assertEquals(cell.getStatus().name(), cellResponse.getStatus());
    }

    private Game createGame() {
        var cell = new Cell(1, 2, false, 2, CellStatus.FLAGGED);
        var board = mock(Board.class);

        when(board.getGameStatus()).thenReturn(GameStatus.PLAYING);
        when(board.getRowsCount()).thenReturn(3);
        when(board.getColumnsCount()).thenReturn(4);
        when(board.getMinesCount()).thenReturn(5);
        when(board.getCells()).thenReturn(new Cell[][] {{ cell }});

        var game = new Game("user-id-test", board);
        game.setLastMoveDate(Instant.now());
        game.setLastResumeDate(Instant.now().plusMillis(10));
        game.setCreatedDate(Instant.now().plusMillis(100));

        return game;
    }
}
