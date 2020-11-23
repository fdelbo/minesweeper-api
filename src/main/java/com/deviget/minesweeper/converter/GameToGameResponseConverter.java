package com.deviget.minesweeper.converter;

import com.deviget.minesweeper.model.api.GameResponse;
import com.deviget.minesweeper.model.document.Game;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
class GameToGameResponseConverter implements Converter<Game, GameResponse> {

    @Override
    public GameResponse convert(Game game) {
        var gameResponse = new GameResponse();

        //TODO
        gameResponse.setId(game.getId());
        gameResponse.setUserId(game.getUserId());
        gameResponse.setCreatedDate(game.getCreatedDate().toString());
        gameResponse.setStatus(game.getBoard().getGameStatus().name());

        return gameResponse;
    }
}
