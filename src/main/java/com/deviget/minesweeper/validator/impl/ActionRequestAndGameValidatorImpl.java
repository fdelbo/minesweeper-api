package com.deviget.minesweeper.validator.impl;

import com.deviget.minesweeper.exception.ValidationException;
import com.deviget.minesweeper.model.api.MakeAMoveRequest;
import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.validator.MakeAnActionRequestAndGameValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * This validator validates that the needed data to make an action within a {@link Game} is valid
 */
@Component
class ActionRequestAndGameValidatorImpl implements MakeAnActionRequestAndGameValidator {

    private static final String ROW_OUT_OF_BOUNDS = "Row is greater than game board bounds";
    private static final String COLUMN_OUT_OF_BOUNDS = "Column is greater than game board bounds";

    @Override
    public void accept(MakeAMoveRequest moveRequest, Game game) {
        var violations = new ArrayList<String>();
        var board = game.getBoard();

        if(moveRequest.getRow() >= board.getRowsCount()) {
            violations.add(ROW_OUT_OF_BOUNDS);
        }

        if(moveRequest.getColumn() >= board.getColumnsCount()) {
            violations.add(COLUMN_OUT_OF_BOUNDS);
        }

        if(!violations.isEmpty()) {
            throw new ValidationException("Invalid request", violations);
        }
    }
}
