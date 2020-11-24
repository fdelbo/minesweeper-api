package com.deviget.minesweeper.model.api;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CreateGameRequest {

    private static final String USER_ID_NOT_BLANK = "UserId cannot be null or empty";
    private static final String ROW_NOT_NEGATIVE_NUMBER = "Rows must be greater than 0";
    private static final String COLUMN_NOT_NEGATIVE_NUMBER = "Columns must be greater than 0";
    private static final String MINES_NOT_NEGATIVE_NUMBER = "Mines must be greater than 0";

    @NotBlank(message = USER_ID_NOT_BLANK)
    private String userId;

    @Min(value = 1, message = ROW_NOT_NEGATIVE_NUMBER)
    private int rows;

    @Min(value = 1, message = COLUMN_NOT_NEGATIVE_NUMBER)
    private int columns;

    @Min(value = 1, message = MINES_NOT_NEGATIVE_NUMBER)
    private int mines;

    public CreateGameRequest() { }

    public CreateGameRequest(String userId, int rows, int columns, int mines) {
        this.userId = userId;
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getMines() {
        return mines;
    }

    public String getUserId() {
        return userId;
    }
}
