package com.deviget.minesweeper.model.api;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CreateGameRequest {

    @NotBlank(message = "UserId cannot be null or empty")
    private String userId;
    @Min(value = 1, message = "Rows must be greater than 0")
    private int rows;
    @Min(value = 1, message = "Columns must be greater than 0")
    private int columns;
    @Min(value = 1, message = "Mines must be greater than 0")
    private int mines;

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
