package com.deviget.minesweeper.model;

import java.util.Random;

final public class Board {

    private final int rowsCount;
    private final int columnsCount;
    private final int minesCount;
    private final Cell[][] cells;

    private GameStatus gameStatus;
    private int openedCells;

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


    public void flipCell(int f, int c) {
        if(cells[f][c].isMine()) {
            gameStatus = GameStatus.LOST;
            //TODO show mines
            return;
        }

        //If cell status is 'closed'
        if(cells[f][c].isClosed()){
            //Set cell status as 'opened'
            cells[f][c].setStatus(CellStatus.OPENED);
            openedCells++;

            if(openedCells == (rowsCount * columnsCount - minesCount)){
                gameStatus = GameStatus.WON;
            } else {

                //If cell has no nearby mines
                if(cells[f][c].hasNoNearbyMines()){

                    //Open nearby mines cells
                    for(int f2 = max(0, f - 1) ; f2 < min(rowsCount,f + 2) ; f2++){
                        for(int c2 = max(0,c - 1) ; c2 < min(columnsCount,c + 2) ; c2++){
                            flipCell(f2, c2);
                        }
                    }
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


    private int min(int a, int b) {
        return Math.min(a, b);
    }

    private int max(int a, int b) {
        return Math.max(a, b);
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
