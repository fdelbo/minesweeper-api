package com.deviget.minesweeper.model.api;

import com.deviget.minesweeper.model.MoveType;
import com.deviget.minesweeper.validator.EnumValue;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class MakeAMoveRequest {

    private static final String USER_ID_NOT_BLANK = "UserId cannot be null or empty";
    private static final String TYPE_NOT_BLANK = "Move type cannot be null or empty";
    private static final String ROW_NOT_NEGATIVE_NUMBER = "Row cannot be less than 0";
    private static final String COLUMN_NOT_NEGATIVE_NUMBER = "Column cannot be less than 0";
    private static final String TYPE_INVALID = "Invalid type value. Valid types are: FLAG, MARK, FLIP";

    @NotBlank(message = USER_ID_NOT_BLANK)
    private String userId;

    @NotBlank(message = TYPE_NOT_BLANK)
    @EnumValue(enumClass = MoveType.class, message = TYPE_INVALID)
    private String type; //FLAG, MARK, FLIP

    @Min(value = 0, message = ROW_NOT_NEGATIVE_NUMBER)
    private int row;

    @Min(value = 0, message = COLUMN_NOT_NEGATIVE_NUMBER)
    private int column;

    public String getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
