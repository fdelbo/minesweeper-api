package com.deviget.minesweeper.model.api;

import java.util.List;

public class GameResponse {
    private String id;
    private String userId;
    private String gameStatus;
    private String createdDate;
    private String lastMoveDate;
    private String lastResumeDate;
    private int rows;
    private int columns;
    private int mines;
    private List<CellResponse> cells;

    public GameResponse() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<CellResponse> getCells() {
        return cells;
    }

    public void setCells(List<CellResponse> cells) {
        this.cells = cells;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public String getLastMoveDate() {
        return lastMoveDate;
    }

    public void setLastMoveDate(String lastMoveDate) {
        this.lastMoveDate = lastMoveDate;
    }

    public String getLastResumeDate() {
        return lastResumeDate;
    }

    public void setLastResumeDate(String lastResumeDate) {
        this.lastResumeDate = lastResumeDate;
    }
}
