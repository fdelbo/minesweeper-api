package com.deviget.minesweeper.converter;

import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.api.CellResponse;
import com.deviget.minesweeper.model.api.GameResponse;
import com.deviget.minesweeper.model.document.Game;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
class GameToGameResponseConverter implements Converter<Game, GameResponse> {

    @Override
    public GameResponse convert(Game game) {
        var gameResponse = new GameResponse();
        var board = game.getBoard();

        gameResponse.setId(game.getId());
        gameResponse.setUserId(game.getUserId());
        gameResponse.setCreatedDate(game.getCreatedDate().toString());
        gameResponse.setGameStatus(board.getGameStatus().name());
        gameResponse.setRows(board.getRowsCount());
        gameResponse.setColumns(board.getColumnsCount());
        gameResponse.setMines(board.getMinesCount());

        var cells = Arrays.stream(board.getCells())
                .flatMap(Arrays::stream)
                .map(cell -> {
                    var cellResp = new CellResponse();
                    cellResp.setColumn(cell.getColumn());
                    cellResp.setRow(cell.getRow());
                    cellResp.setMine(cell.isMine());
                    cellResp.setNearbyMines(cell.getNearbyMines());
                    cellResp.setStatus(cell.getStatus().name());

                    return cellResp;
                })
                .collect(Collectors.toList());

        gameResponse.setCells(cells);

        return gameResponse;
    }
}
