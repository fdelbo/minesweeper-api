package com.deviget.minesweeper.model;

import java.util.Objects;

public class Cell {

    private int row;
    private int column;
    private boolean mine;
    private int nearbyMines;
    private CellStatus status;

    public Cell(int row, int column, boolean mine, int nearbyMines, CellStatus status) {
        this.row = row;
        this.column = column;
        this.mine = mine;
        this.nearbyMines = nearbyMines;
        this.status = status;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isMine() {
        return mine;
    }

    public boolean isNotMine() {
        return !mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getNearbyMines() {
        return nearbyMines;
    }

    public void setNearbyMines(int nearbyMines) {
        this.nearbyMines = nearbyMines;
    }

    public void addNearbyMine() {
        this.nearbyMines++;
    }

    public CellStatus getStatus() {
        return status;
    }

    public boolean isClosed() {
        return CellStatus.CLOSED.equals(status);
    }

    public boolean hasNoNearbyMines() {
        return !(nearbyMines > 0);
    }

    public void setStatus(CellStatus status) {
        this.status = status;
    }

    public boolean isOpened() {
        return CellStatus.OPENED.equals(status);
    }

    public boolean isFlagged() {
        return CellStatus.FLAGGED.equals(status);
    }

    public boolean isMarked() {
        return CellStatus.MARKED.equals(status);
    }

    @Override
    public String toString() {
        return "Row: " + row + " - Column: " + column + " - Mine: " +
                mine + " - Nearby: " + nearbyMines + " - Status: " +
                status.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row &&
                column == cell.column &&
                mine == cell.mine &&
                nearbyMines == cell.nearbyMines &&
                status == cell.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column, mine, nearbyMines, status);
    }
}
