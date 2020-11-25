package com.deviget.minesweeper.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void givenABoardWithSettings_returnABoardInitialized() {
        //Given
        var rows = 2;
        var columns = 2;
        var mines = 1;

        //When
        var board = new Board(rows, columns, mines);

        //Then
        assertEquals(GameStatus.PLAYING, board.getGameStatus());
        var cells = board.getCells();
        for(int r = 0 ; r < board.getRowsCount() ; r++) {
            for(int c = 0 ; c < board.getColumnsCount() ; c++) {
                var cell = cells[r][c];
                assertEquals(CellStatus.CLOSED, cell.getStatus());
                assertFalse(cell.isMine());
                assertEquals(0, cell.getNearbyMines());
                assertEquals(c, cell.getColumn());
                assertEquals(r, cell.getRow());
            }
        }
    }

    @Test
    void givenABoardWithSettings_whenSetup_returnABoardFullyConfigured() {
        //Given
        var rows = 2;
        var columns = 2;
        var mines = 1;
        var board = new Board(rows, columns, mines);

        //When
        board.setup();

        //Then
        assertEquals(GameStatus.PLAYING, board.getGameStatus());
        var cells = board.getCells();
        var minesCount = 0;
        for(int r = 0 ; r < board.getRowsCount() ; r++) {
            for(int c = 0 ; c < board.getColumnsCount() ; c++) {
                var cell = cells[r][c];
                if(cell.isMine()) {
                    minesCount++;
                }
                assertEquals(CellStatus.CLOSED, cell.getStatus());
                assertEquals(c, cell.getColumn());
                assertEquals(r, cell.getRow());
            }
        }

        assertEquals(mines, minesCount);
    }

    @Test
    void givenAMinedCoordinate_whenFlipCell_thenGameStatusIsGameOverAndMinesAreShown() {
        //Given
        var rowMine = 0;
        var columnMine = 0;
        var cells = new Cell[][] {
                { new Cell(0, 0, true, 0, CellStatus.CLOSED),
                        new Cell(0, 1, false, 1, CellStatus.CLOSED) },
                { new Cell(1, 0, false, 1, CellStatus.CLOSED),
                        new Cell(1, 1, true, 1, CellStatus.CLOSED) }};
        var board = new Board(cells, 2, 2, 1);

        //When
        board.flipCell(rowMine, columnMine);

        //Then
        assertEquals(GameStatus.GAME_OVER, board.getGameStatus());
        assertEquals(CellStatus.OPENED, cells[0][0].getStatus());
        assertEquals(CellStatus.CLOSED, cells[0][1].getStatus());
        assertEquals(CellStatus.CLOSED, cells[1][0].getStatus());
        assertEquals(CellStatus.OPENED, cells[1][1].getStatus());
    }

    @Test
    void givenAnInitialGameSetup_whenFlipANotMinedCell_thenFlipTheCellAndItsNoNearbyMineNeighbours() {
        var rows = 5;
        var columns = 5;
        var mines = 2;

        //Given
        var expectedResult = new Cell[][] {
                { new Cell(0, 0, false, 0, CellStatus.OPENED),
                        new Cell(0, 1, false, 0, CellStatus.OPENED),
                        new Cell(0, 2, false, 0, CellStatus.OPENED),
                        new Cell(0, 3, false, 0, CellStatus.OPENED),
                        new Cell(0, 4, false, 0, CellStatus.OPENED)
                },
                { new Cell(1, 0, false, 0, CellStatus.OPENED),
                        new Cell(1, 1, false, 0, CellStatus.OPENED),
                        new Cell(1, 2, false, 0, CellStatus.OPENED),
                        new Cell(1, 3, false, 0, CellStatus.OPENED),
                        new Cell(1, 4, false, 0, CellStatus.OPENED)
                },
                { new Cell(2, 0, false, 1, CellStatus.OPENED),
                        new Cell(2, 1, false, 1, CellStatus.OPENED),
                        new Cell(2, 2, false, 1, CellStatus.OPENED),
                        new Cell(2, 3, false, 0, CellStatus.OPENED),
                        new Cell(2, 4, false, 0, CellStatus.OPENED)
                },
                { new Cell(3, 0, false, 1, CellStatus.CLOSED),
                        new Cell(3, 1, true, 0, CellStatus.CLOSED),
                        new Cell(3, 2, false, 2, CellStatus.OPENED),
                        new Cell(3, 3, false, 1, CellStatus.OPENED),
                        new Cell(3, 4, false, 0, CellStatus.OPENED)
                },
                { new Cell(4, 0, false, 1, CellStatus.CLOSED),
                        new Cell(4, 1, false, 2, CellStatus.CLOSED),
                        new Cell(4, 2, true, 0, CellStatus.CLOSED),
                        new Cell(4, 3, false, 1, CellStatus.OPENED),
                        new Cell(4, 4, false, 0, CellStatus.OPENED)
                }};

        var initialCells = new Cell[][] {
                { new Cell(0, 0, false, 0, CellStatus.CLOSED),
                        new Cell(0, 1, false, 0, CellStatus.CLOSED),
                        new Cell(0, 2, false, 0, CellStatus.CLOSED),
                        new Cell(0, 3, false, 0, CellStatus.CLOSED),
                        new Cell(0, 4, false, 0, CellStatus.CLOSED)
                },
                { new Cell(1, 0, false, 0, CellStatus.CLOSED),
                        new Cell(1, 1, false, 0, CellStatus.CLOSED),
                        new Cell(1, 2, false, 0, CellStatus.CLOSED),
                        new Cell(1, 3, false, 0, CellStatus.CLOSED),
                        new Cell(1, 4, false, 0, CellStatus.CLOSED)
                },
                { new Cell(2, 0, false, 1, CellStatus.CLOSED),
                        new Cell(2, 1, false, 1, CellStatus.CLOSED),
                        new Cell(2, 2, false, 1, CellStatus.CLOSED),
                        new Cell(2, 3, false, 0, CellStatus.CLOSED),
                        new Cell(2, 4, false, 0, CellStatus.CLOSED)
                },
                { new Cell(3, 0, false, 1, CellStatus.CLOSED),
                        new Cell(3, 1, true, 0, CellStatus.CLOSED),
                        new Cell(3, 2, false, 2, CellStatus.CLOSED),
                        new Cell(3, 3, false, 1, CellStatus.CLOSED),
                        new Cell(3, 4, false, 0, CellStatus.CLOSED)
                },
                { new Cell(4, 0, false, 1, CellStatus.CLOSED),
                        new Cell(4, 1, false, 2, CellStatus.CLOSED),
                        new Cell(4, 2, true, 0, CellStatus.CLOSED),
                        new Cell(4, 3, false, 1, CellStatus.CLOSED),
                        new Cell(4, 4, false, 0, CellStatus.CLOSED)
                }};

        var board = new Board(initialCells, rows, columns, mines);

        //When
        board.flipCell(1, 3);

        //Then
        assertTrue(equals(expectedResult, board.getCells(), rows, columns));
        assertEquals(GameStatus.PLAYING, board.getGameStatus());
    }

    @Test
    void givenAnInitialGameSetup_whenFlipANotMinedCellWithNearbyMines_thenJustFlipTheCell() {
        var rows = 3;
        var columns = 3;
        var mines = 1;
        var expectedResult = new Cell[][] {
                { new Cell(0, 0, false, 1, CellStatus.CLOSED),
                        new Cell(0, 1, false, 1, CellStatus.CLOSED),
                        new Cell(0, 2, false, 1, CellStatus.OPENED)
                },
                { new Cell(1, 0, false, 1, CellStatus.CLOSED),
                        new Cell(1, 1, true, 0, CellStatus.CLOSED),
                        new Cell(1, 2, false, 1, CellStatus.CLOSED)
                },
                { new Cell(2, 0, false, 1, CellStatus.CLOSED),
                        new Cell(2, 1, false, 1, CellStatus.CLOSED),
                        new Cell(2, 2, false, 1, CellStatus.CLOSED)}};

        var initialCells = new Cell[][] {
                { new Cell(0, 0, false, 1, CellStatus.CLOSED),
                        new Cell(0, 1, false, 1, CellStatus.CLOSED),
                        new Cell(0, 2, false, 1, CellStatus.CLOSED)
                },
                { new Cell(1, 0, false, 1, CellStatus.CLOSED),
                        new Cell(1, 1, true, 0, CellStatus.CLOSED),
                        new Cell(1, 2, false, 1, CellStatus.CLOSED)
                },
                { new Cell(2, 0, false, 1, CellStatus.CLOSED),
                        new Cell(2, 1, false, 1, CellStatus.CLOSED),
                        new Cell(2, 2, false, 1, CellStatus.CLOSED)}};

        var board = new Board(initialCells, rows, columns, mines);

        //When
        board.flipCell(0, 2);

        //Then
        assertTrue(equals(expectedResult, board.getCells(), rows, columns));
        assertEquals(GameStatus.PLAYING, board.getGameStatus());
    }

    @Test
    void givenAnInitialGameSetup_whenToggleFlag_thenJustFlagTheCell() {
        var rows = 2;
        var columns = 3;
        var mines = 1;
        var expectedResult = new Cell[][] {
                { new Cell(0, 0, false, 1, CellStatus.CLOSED),
                        new Cell(0, 1, false, 1, CellStatus.CLOSED),
                        new Cell(0, 2, false, 1, CellStatus.FLAGGED)
                },
                { new Cell(1, 0, false, 1, CellStatus.CLOSED),
                        new Cell(1, 1, true, 0, CellStatus.CLOSED),
                        new Cell(1, 2, false, 1, CellStatus.CLOSED)
                }};

        var initialCells = new Cell[][] {
                { new Cell(0, 0, false, 1, CellStatus.CLOSED),
                        new Cell(0, 1, false, 1, CellStatus.CLOSED),
                        new Cell(0, 2, false, 1, CellStatus.CLOSED)
                },
                { new Cell(1, 0, false, 1, CellStatus.CLOSED),
                        new Cell(1, 1, true, 0, CellStatus.CLOSED),
                        new Cell(1, 2, false, 1, CellStatus.CLOSED)
                }};

        var board = new Board(initialCells, rows, columns, mines);

        //When
        board.toggleFlag(0, 2);

        //Then
        assertTrue(equals(expectedResult, board.getCells(), rows, columns));
        assertEquals(GameStatus.PLAYING, board.getGameStatus());
    }

    @Test
    void givenAnInitialGameSetup_whenToggleMark_thenJustMarkTheCell() {
        var rows = 2;
        var columns = 3;
        var mines = 1;
        var expectedResult = new Cell[][] {
                { new Cell(0, 0, false, 1, CellStatus.CLOSED),
                        new Cell(0, 1, false, 1, CellStatus.CLOSED),
                        new Cell(0, 2, false, 1, CellStatus.MARKED)
                },
                { new Cell(1, 0, false, 1, CellStatus.CLOSED),
                        new Cell(1, 1, true, 0, CellStatus.CLOSED),
                        new Cell(1, 2, false, 1, CellStatus.CLOSED)
                }};

        var initialCells = new Cell[][] {
                { new Cell(0, 0, false, 1, CellStatus.CLOSED),
                        new Cell(0, 1, false, 1, CellStatus.CLOSED),
                        new Cell(0, 2, false, 1, CellStatus.CLOSED)
                },
                { new Cell(1, 0, false, 1, CellStatus.CLOSED),
                        new Cell(1, 1, true, 0, CellStatus.CLOSED),
                        new Cell(1, 2, false, 1, CellStatus.CLOSED)
                }};

        var board = new Board(initialCells, rows, columns, mines);

        //When
        board.toggleMark(0, 2);

        //Then
        assertTrue(equals(expectedResult, board.getCells(), rows, columns));
        assertEquals(GameStatus.PLAYING, board.getGameStatus());
    }

    @Test
    void givenAnInitialGameSetup_whenToggleFlagAndThenRemove_thenCellBecomesStatusClosed() {
        var rows = 2;
        var columns = 3;
        var mines = 1;
        var expectedResult = new Cell[][] {
                { new Cell(0, 0, false, 1, CellStatus.CLOSED),
                        new Cell(0, 1, false, 1, CellStatus.CLOSED),
                        new Cell(0, 2, false, 1, CellStatus.CLOSED)
                },
                { new Cell(1, 0, false, 1, CellStatus.CLOSED),
                        new Cell(1, 1, true, 0, CellStatus.CLOSED),
                        new Cell(1, 2, false, 1, CellStatus.CLOSED)
                }};

        var initialCells = new Cell[][] {
                { new Cell(0, 0, false, 1, CellStatus.CLOSED),
                        new Cell(0, 1, false, 1, CellStatus.CLOSED),
                        new Cell(0, 2, false, 1, CellStatus.CLOSED)
                },
                { new Cell(1, 0, false, 1, CellStatus.CLOSED),
                        new Cell(1, 1, true, 0, CellStatus.CLOSED),
                        new Cell(1, 2, false, 1, CellStatus.CLOSED)
                }};

        var board = new Board(initialCells, rows, columns, mines);

        //When
        board.toggleFlag(0, 2);
        board.removeFlagOrMark(0, 2);

        //Then
        assertTrue(equals(expectedResult, board.getCells(), rows, columns));
        assertEquals(GameStatus.PLAYING, board.getGameStatus());
    }

    @Test
    void givenAnInitialGameSetup_whenAllMinesAreDiscovered_thenGameStatusIsWon() {
        var rows = 3;
        var columns = 3;
        var mines = 2;
        var expectedResult = new Cell[][] {
                { new Cell(0, 0, true, 0, CellStatus.OPENED),
                        new Cell(0, 1, false, 1, CellStatus.OPENED),
                        new Cell(0, 2, false, 0, CellStatus.OPENED)
                },
                { new Cell(1, 0, false, 1, CellStatus.OPENED),
                        new Cell(1, 1, false, 2, CellStatus.OPENED),
                        new Cell(1, 2, false, 1, CellStatus.OPENED)
                },
                { new Cell(2, 0, false, 0, CellStatus.OPENED),
                        new Cell(2, 1, false, 1, CellStatus.OPENED),
                        new Cell(2, 2, true, 0, CellStatus.OPENED)
                }};

        var initialCells = new Cell[][] {
                { new Cell(0, 0, true, 0, CellStatus.CLOSED),
                        new Cell(0, 1, false, 1, CellStatus.CLOSED),
                        new Cell(0, 2, false, 0, CellStatus.CLOSED)
                },
                { new Cell(1, 0, false, 1, CellStatus.CLOSED),
                        new Cell(1, 1, false, 2, CellStatus.CLOSED),
                        new Cell(1, 2, false, 1, CellStatus.CLOSED)
                },
                { new Cell(2, 0, false, 0, CellStatus.CLOSED),
                        new Cell(2, 1, false, 1, CellStatus.CLOSED),
                        new Cell(2, 2, true, 0, CellStatus.CLOSED)
                }};

        var board = new Board(initialCells, rows, columns, mines);

        //When
        board.flipCell(0, 2);
        assertEquals(GameStatus.PLAYING, board.getGameStatus());
        board.flipCell(2, 0);

        //Then
        assertTrue(equals(expectedResult, board.getCells(), rows, columns));
        assertEquals(GameStatus.WON, board.getGameStatus());
    }


    private boolean equals(Cell[][] cells1, Cell[][] cells2, int rows, int columns) {
        if(cells1.length != cells2.length) {
            return false;
        }

        for(int r = 0 ; r < rows ; r++) {
            for(int c = 0 ; c < columns ; c++) {
                if(!cells1[r][c].equals(cells2[r][c])) {
                    return false;
                }
            }
        }

        return true;
    }


}
