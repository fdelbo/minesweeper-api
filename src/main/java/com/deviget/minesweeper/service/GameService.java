package com.deviget.minesweeper.service;

import com.deviget.minesweeper.model.api.CreateGameRequest;
import com.deviget.minesweeper.model.document.Game;

public interface GameService {
    Game create(CreateGameRequest request);
}
