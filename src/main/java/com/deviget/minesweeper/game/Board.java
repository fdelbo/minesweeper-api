package com.deviget.minesweeper.game;

import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.CellStatus;
import com.deviget.minesweeper.model.GameStatus;

import java.util.Random;

/**
 * This class is the representation of the game board.
 * It contains the necessary methods to manage the minesweeper.
 */
final public class Board {

    private int rowsCount;
    private int columnsCount;
    private int minesCount;
    private Cell[][] cells;

    private GameStatus gameStatus;
    private int openedCells;

    public Board() {
    }

    public Board(int rows, int columns, int mines) {
        this.cells = new Cell[rows][columns];
        this.minesCount = mines;
        this.rowsCount = rows;
        this.columnsCount = columns;
        this.gameStatus = GameStatus.PLAYING;
        initialize();
    }

    /**
     * This method initializes the board with a default {@link Cell} matrix
     */
    private void initialize() {
        for(int r = 0 ; r < rowsCount ; r++) {
            for(int c = 0 ; c < columnsCount ; c++) {
                cells[r][c] = new Cell(r, c, false, 0, CellStatus.CLOSED);
            }
        }
    }

    /**
     * This method configures the board.
     * It sets all the mines and calculates the nearby mines in each cell.
     */
    public void setup() {
        var randomInt = new Random();
        var totalMinesSet = 0;

        while(minesCount > totalMinesSet) {
            int randomRow;
            int randomColumn;

            do {
                randomRow = randomInt.nextInt(rowsCount);
                randomColumn = randomInt.nextInt(columnsCount);
            } while(cells[randomRow][randomColumn].isMine());

            cells[randomRow][randomColumn].setMine(true);
            totalMinesSet++;

            for(int row = max(0, randomRow - 1) ; row < min(rowsCount, randomRow + 2) ; row++) {
                for(int column = max(0, randomColumn - 1) ; column < min(columnsCount, randomColumn + 2) ; column++) {
                    if(cells[row][column].isNotMine()) {
                        cells[row][column].addNearbyMine();
                    }
                }
            }
        }

    }

    /**
     * This methods flips a {@link Cell}
     *
     * @param r is the row that in combination with a column defines a {@link Cell} that should be flipped
     * @param c is the column that in combination with a row defines a {@link Cell} that should be flipped
     */
    public void flipCell(int r, int c) {
        if(cells[r][c].isMine()) {
            gameStatus = GameStatus.GAME_OVER;
            openMines();
            return;
        }

        flipCellAndNeighbours(r, c);
    }

    /**
     * Sets a flag to a given {@link Cell}
     * @param r is the row that in combination with a column defines a {@link Cell}
     * @param c is the column that in combination with a row defines a {@link Cell}
     */
    public void toggleFlag(int r, int c) {
        var cell = cells[r][c];
        if(cell.isClosed()) {
            cell.setStatus(CellStatus.FLAGGED);
        } else if(cell.isFlagged()) {
            cell.setStatus(CellStatus.CLOSED);
        }
    }

    /**
     * Sets a mark to a given {@link Cell}
     * @param r is the row that in combination with a column defines a {@link Cell}
     * @param c is the column that in combination with a row defines a {@link Cell}
     */
    public void toggleMark(int r, int c) {
        var cell = cells[r][c];
        if(cell.isClosed()) {
            cell.setStatus(CellStatus.MARKED);
        } else if(cell.isMarked()) {
            cell.setStatus(CellStatus.CLOSED);
        }
    }

    /**
     * Sets a {@link Cell} to a CLOSED {@link CellStatus} if the {@link CellStatus} previously is
     * FLAGGED or MARKED
     * @param r is the row that in combination with a column defines a {@link Cell}
     * @param c is the column that in combination with a row defines a {@link Cell}
     */
    public void removeFlagOrMark(int r, int c) {
        var cell = cells[r][c];
        if(cell.isFlagged() || cell.isMarked()) {
            cell.setStatus(CellStatus.CLOSED);
        }
    }

    /**
     * This method flips neighboud cells given both a row and a column
     *
     * @param r is the row that in combination with a column defines a {@link Cell} that should be flipped
     * @param c is the column that in combination with a row defines a {@link Cell} that should be flipped
     */
    private void flipCellAndNeighbours(int r, int c) {
        //If cell status is 'closed'
        if(cells[r][c].isClosed()){
            //Set cell status as 'opened'
            cells[r][c].setStatus(CellStatus.OPENED);
            openedCells++;

            if(openedCells == (rowsCount * columnsCount - minesCount)){
                gameStatus = GameStatus.WON;
            } else {

                //If cell has no nearby mines
                if(cells[r][c].hasNoNearbyMines()){

                    //Open nearby mines cells
                    for(int f2 = max(0, r - 1) ; f2 < min(rowsCount,r + 2) ; f2++){
                        for(int c2 = max(0,c - 1) ; c2 < min(columnsCount,c + 2) ; c2++){
                            flipCellAndNeighbours(f2, c2);
                        }
                    }
                }
            }
        }
    }

    /**
     * This method opens every mine the board has
     */
    private void openMines() {
        for(int r = 0 ; r < rowsCount ; r++) {
            for(int c = 0 ; c < columnsCount ; c++) {
                var cell = cells[r][c];
                if(cell.isMine()) {
                    cell.setStatus(CellStatus.OPENED);
                }
            }
        }
    }

    @Override
    public String toString() {
        var sb = new StringBuilder("\n");

        for(int r=0 ; r < rowsCount ; r++) {
            for(int c=0 ; c < columnsCount ; c++) {
                sb.append("[")
                        .append(String.format("%s %s %s", statusSymbol(cells[r][c]),
                                cells[r][c].isMine() ? "#" : "-",
                                cells[r][c].getNearbyMines()))
                        .append("]")
                        .append(" ");
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    private String statusSymbol(Cell cell) {
        var symbol = "";
        var cellStatus = cell.getStatus();
        switch (cellStatus) {
            case CLOSED:
                symbol = "C";
                break;
            case OPENED:
                symbol = "O";
                break;
            case FLAGGED:
                symbol = "F";
                break;
        }

        return symbol;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setStatus(GameStatus status) {
        this.gameStatus = status;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColumnsCount() {
        return columnsCount;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public Cell[][] getCells() {
        return cells;
    }

    private int min(int a, int b) {
        return Math.min(a, b);
    }

    private int max(int a, int b) {
        return Math.max(a, b);
    }

}
